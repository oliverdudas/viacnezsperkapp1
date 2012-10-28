package sk.dudas.appengine.viacnezsperk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.beans.support.SortDefinition;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import sk.dudas.appengine.viacnezsperk.controller.bind.CustomUserPasswordBinder;
import sk.dudas.appengine.viacnezsperk.domain.Role;
import sk.dudas.appengine.viacnezsperk.domain.User;
import sk.dudas.appengine.viacnezsperk.service.UserManager;

import javax.servlet.http.HttpServletRequest;
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
        modelMap.addAttribute("holder", pagedListHolder);

        ServletRequestDataBinder binder = new ServletRequestDataBinder(pagedListHolder, "holder");
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

    @RequestMapping(value = ADMIN_CHILD_FORM_VIEW, method = RequestMethod.POST, params = "!cancel")
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

    @RequestMapping(value = ADMIN_CHILD_FORM_VIEW, method = RequestMethod.POST, params = "cancel")
    public String cancel() {
        return "redirect:" + ADMIN_CHILDREN_VIEW;
    }
}
