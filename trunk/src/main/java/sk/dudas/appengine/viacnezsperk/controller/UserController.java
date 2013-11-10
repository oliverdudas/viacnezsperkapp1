package sk.dudas.appengine.viacnezsperk.controller;

import com.google.appengine.api.datastore.Text;
import com.google.gdata.data.MediaContent;
import com.google.gdata.data.photos.PhotoEntry;
import com.google.gdata.util.ServiceException;
import org.gmr.web.multipart.GMultipartFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;
import sk.dudas.appengine.viacnezsperk.controller.bind.CustomTextBinder;
import sk.dudas.appengine.viacnezsperk.controller.validator.UserValidator;
import sk.dudas.appengine.viacnezsperk.domain.GalleryItem;
import sk.dudas.appengine.viacnezsperk.domain.User;
import sk.dudas.appengine.viacnezsperk.domain.UserBrowse;
import sk.dudas.appengine.viacnezsperk.service.PicasaManager;
import sk.dudas.appengine.viacnezsperk.service.UserBrowseManager;
import sk.dudas.appengine.viacnezsperk.service.UserManager;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: oli
 * Date: 15.10.2012
 * Time: 22:19
 * To change this template use File | Settings | File Templates.
 */
@Controller("userController")
@SessionAttributes(value = UserController.CHILD_COMMAND)
public class UserController {

    private static final Logger logger = Logger.getLogger(UserController.class.getName());
    public static final String ADMIN_CHILD_FORM_VIEW = "/admin/childForm";
    public static final String ADMIN_CHILDREN_VIEW = "/admin/children";
    public static final String CHILD_COMMAND = "child";
    private static final String LIST_HOLDER = "listHolder";
    private static final String HOLDER = "holder";
    public static final String ADMIN_CHILD_DELETE = "/admin/delete";
    public static final String MODIFIED = "modified";
    public static final String SEARCH_VALUE = "searchValue";
    private static final String LAST_SORT = "lastSort";

    @Autowired
    private UserManager userManager;

    @Autowired
    private UserBrowseManager userBrowseManager;

    @Autowired
    private PicasaManager picasaManager;

    @Autowired
    private UserValidator userValidator;

    @InitBinder(value = UserController.CHILD_COMMAND)
    public void initBinder(WebDataBinder binder) {
//        binder.registerCustomEditor(String.class, "password", new CustomUserPasswordBinder());
        binder.registerCustomEditor(Text.class, new CustomTextBinder());
    }

    @RequestMapping(value = ADMIN_CHILDREN_VIEW, method = RequestMethod.POST)
    public String search(HttpServletRequest request, @RequestParam(defaultValue = "") String searchValue) {
        request.getSession().setAttribute(SEARCH_VALUE, searchValue);
        return "redirect:" + ADMIN_CHILDREN_VIEW;
    }

    @RequestMapping(value = ADMIN_CHILDREN_VIEW, method = RequestMethod.GET)
    public void list(HttpServletRequest request, ModelMap modelMap, @RequestParam(defaultValue = "0") int p) {
        logger.log(Level.INFO, "Zoznam deti");

        PagedListHolder<UserBrowse> pagedListHolder;
        List<UserBrowse> all = userBrowseManager.getUsers((String) request.getSession().getAttribute(SEARCH_VALUE));
        pagedListHolder = new PagedListHolder<UserBrowse>(all);
        int pageSize = 25;
        pagedListHolder.setPageSize(pageSize);
        MutableSortDefinition sort = (MutableSortDefinition) pagedListHolder.getSort();
        sort.setProperty(MODIFIED);
        sort.setAscending(false);
        modelMap.addAttribute(HOLDER, pagedListHolder);

        ServletRequestDataBinder binder = new ServletRequestDataBinder(pagedListHolder, HOLDER);
        binder.bind(request);

        pagedListHolder.resort();
        pagedListHolder.setPage(p);
    }

    @RequestMapping(value = ADMIN_CHILD_FORM_VIEW, method = RequestMethod.GET)
    public void form(ModelMap modelMap, @RequestParam(required = false, defaultValue = "0") long id) {
        User child;
        if (id == 0) {
            logger.log(Level.INFO, "Creating child.");
            child = new User();
        } else {
            logger.log(Level.INFO, "Editing child.");
            child = userManager.findUserById(id);
        }
        modelMap.addAttribute(CHILD_COMMAND, child);
    }

    @RequestMapping(value = ADMIN_CHILD_FORM_VIEW, method = RequestMethod.POST, params = "delete")
    public String delete(@ModelAttribute(value = CHILD_COMMAND) User child, HttpServletRequest request) {
        if (child.getKey() != null) {
            userManager.removeUser(child.getKey());
        }
        return "redirect:" + ADMIN_CHILDREN_VIEW;
    }

    @RequestMapping(value = ADMIN_CHILD_FORM_VIEW, method = RequestMethod.POST, params = "childFormSubmit")
    public String form(HttpServletRequest request,
                       @ModelAttribute(value = CHILD_COMMAND) User child,
                       BindingResult bindingResult,
                       SessionStatus sessionStatus) {

        userValidator.validate(child, bindingResult);
        if (bindingResult.hasErrors()) {
            return "admin/childForm";
        } else {
            userManager.persistOrMergeNonTransactionalUser(child);
            sessionStatus.setComplete();
            return "redirect:" + ADMIN_CHILDREN_VIEW;
        }
    }

    @RequestMapping(value = "/admin/uploadTest")
    public
    @ResponseBody
    String uploadTest(@ModelAttribute(value = CHILD_COMMAND) User child, HttpServletRequest request) throws IOException, ServiceException {
        PhotoEntry returnedPhoto = uploadPhotoToPicasa((DefaultMultipartHttpServletRequest) request);

        String photoUri = ((MediaContent) returnedPhoto.getContent()).getUri();
        child.setMainURL(photoUri);

        return photoUri;
    }

    @RequestMapping(value = "/admin/uploadGalleryItem")
    public
    @ResponseBody
    GalleryItem galleryItemUpload(@ModelAttribute(value = CHILD_COMMAND) User child, HttpServletRequest request) throws IOException, ServiceException {
        PhotoEntry returnedPhoto = uploadPhotoToPicasa((DefaultMultipartHttpServletRequest) request);

        String imageUrl = ((MediaContent) returnedPhoto.getContent()).getUri();
        String thumbUrl = returnedPhoto.getMediaThumbnails().get(1).getUrl();
        String gphotoId = returnedPhoto.getGphotoId();
        GalleryItem item = new GalleryItem(gphotoId, imageUrl, thumbUrl);
        child.addGalleryItem(item);

        return item;
    }

    private PhotoEntry uploadPhotoToPicasa(DefaultMultipartHttpServletRequest request) throws IOException, ServiceException {
        GMultipartFile file = (GMultipartFile) ((DefaultMultipartHttpServletRequest) request).getFileMap().get("file");
        return picasaManager.uploadPhotoToPicasa(file);
    }

    @RequestMapping(value = "/admin/deleteGalleryItem")
    public
    @ResponseBody
    String deleteGalleryItem(@ModelAttribute(value = CHILD_COMMAND) User child, @RequestParam(value = "gphotoId", required = true) String gphotoId) {
        List<GalleryItem> galleryItems = (List<GalleryItem>) child.getGalleryItems();
        if (galleryItems != null) {
            for (GalleryItem galleryItem : galleryItems) {
                if (gphotoId.equals(galleryItem.getGphotoId())) {
                    galleryItems.remove(galleryItem);
                    break;
                }
            }

        }

        return String.valueOf(true);
    }

    @RequestMapping(value = ADMIN_CHILD_FORM_VIEW, method = RequestMethod.POST, params = "cancel")
    public String cancel() {
        return "redirect:" + ADMIN_CHILDREN_VIEW;
    }

}
