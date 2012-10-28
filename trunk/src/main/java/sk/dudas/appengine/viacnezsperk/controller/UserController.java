package sk.dudas.appengine.viacnezsperk.controller;

import com.google.appengine.api.datastore.Blob;
import com.google.gdata.client.photos.PicasawebService;
import com.google.gdata.data.MediaContent;
import com.google.gdata.data.PlainTextConstruct;
import com.google.gdata.data.media.MediaByteArraySource;
import com.google.gdata.data.media.MediaFileSource;
import com.google.gdata.data.media.MediaStreamSource;
import com.google.gdata.data.photos.PhotoEntry;
import com.google.gdata.util.AuthenticationException;
import com.google.gdata.util.ServiceException;
import org.gmr.web.multipart.GMultipartFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;
import sk.dudas.appengine.viacnezsperk.controller.bind.CustomUserPasswordBinder;
import sk.dudas.appengine.viacnezsperk.domain.Role;
import sk.dudas.appengine.viacnezsperk.domain.User;
import sk.dudas.appengine.viacnezsperk.service.UserManager;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
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

    @Autowired
    private UserManager userManager;

    @InitBinder(value = UserController.CHILD_COMMAND)
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, "password", new CustomUserPasswordBinder());
    }

    @RequestMapping(value = ADMIN_CHILDREN_VIEW, method = RequestMethod.GET)
    public void list(HttpServletRequest request, ModelMap modelMap, @RequestParam(defaultValue = "0") int p) {
        logger.log(Level.INFO, "Zoznam deti");

        PagedListHolder pagedListHolder = (PagedListHolder) request.getSession().getAttribute(LIST_HOLDER);
        if (pagedListHolder == null) {
            List<User> all = userManager.findAll();
            pagedListHolder = new PagedListHolder(all);
            request.getSession().setAttribute(LIST_HOLDER, pagedListHolder);
            int pageSize = 4;
            pagedListHolder.setPageSize(pageSize);
        }
        pagedListHolder.setPage(p);
        modelMap.addAttribute(HOLDER, pagedListHolder);

        ServletRequestDataBinder binder = new ServletRequestDataBinder(pagedListHolder, HOLDER);
        binder.bind(request);

        pagedListHolder.resort();
    }

    @RequestMapping(value = ADMIN_CHILD_FORM_VIEW, method = RequestMethod.GET)
    public void form(ModelMap modelMap, @RequestParam(required = false, defaultValue = "0") long id) {
        User child;
        if (id == 0) {
            logger.log(Level.INFO, "Creating child.");
            child = new User();
        } else {
            logger.log(Level.INFO, "Editing child.");
            child = userManager.findById(id);
        }
        modelMap.addAttribute(CHILD_COMMAND, child);
    }

    @RequestMapping(value = ADMIN_CHILD_FORM_VIEW, method = RequestMethod.POST, params = "childFormSubmit")
    public String form(@ModelAttribute(value = CHILD_COMMAND) User child, BindingResult bindingResult, SessionStatus sessionStatus) {
        if (bindingResult.hasErrors()) {
            return ADMIN_CHILD_FORM_VIEW;
        } else {
            addDefaultRole(child);
            userManager.persistOrMergeUser(child);
            return "redirect:" + ADMIN_CHILDREN_VIEW;
        }
    }

    private void addDefaultRole(User child) {
        ArrayList<Role> roles = new ArrayList<Role>();
        roles.add(new Role(Role.ROLE_USER));
        child.setRoles(roles);
    }

    @RequestMapping(value = ADMIN_CHILD_FORM_VIEW, method = RequestMethod.POST, params = "uploadImage")
    public void upload(@ModelAttribute(value = CHILD_COMMAND) User child, HttpServletRequest request) throws ServiceException, IOException {
        GMultipartFile file = (GMultipartFile) ((DefaultMultipartHttpServletRequest) request).getFileMap().get("file");

        String userId = "104004273393078620402"; //oliver.dudas@viacnezsperk.sk
        String albumid = "5804367311941076033"; //viacnezsperk album on oliver.dudas@viacnezsperk.sk account

        URL albumPostUrl = new URL("https://picasaweb.google.com/data/feed/api/user/" + userId + "/albumid/" + albumid);

        PhotoEntry myPhoto = new PhotoEntry();
        myPhoto.setTitle(new PlainTextConstruct(file.getOriginalFilename()));
        myPhoto.setDescription(new PlainTextConstruct(file.getOriginalFilename()));
//        myPhoto.setClient("myClientName");

        MediaStreamSource myMedia = new MediaStreamSource(file.getInputStream(), "image/jpeg");
        myPhoto.setMediaSource(myMedia);

        PicasawebService myService = new PicasawebService("viacnezsperk");
        myService.setReadTimeout(1000000);
        myService.setConnectTimeout(1000000);
        myService.setUserCredentials("oliver.dudas@viacnezsperk.sk", "sperk123");

        PhotoEntry returnedPhoto = myService.insert(albumPostUrl, myPhoto);

        String photoUri = ((MediaContent) returnedPhoto.getContent()).getUri();
        child.setMainURL(photoUri);
    }

    @RequestMapping(value = ADMIN_CHILD_FORM_VIEW, method = RequestMethod.POST, params = "cancel")
    public String cancel() {
        return "redirect:" + ADMIN_CHILDREN_VIEW;
    }

}
