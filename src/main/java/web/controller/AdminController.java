package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.model.Role;
import web.model.User;
import web.service.RoleService;
import web.service.UserService;

import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String printAllUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "users/administrator";
    }

    @GetMapping("/new")
    public String newUser(Model model) {
        model.addAttribute("user", new User());
        return "users/new_user";
    }

    @PostMapping()
    public String postNewUser(@ModelAttribute("user") User user,
                              @RequestParam(required = false) String[] role) {
        Set<Role> sr = new HashSet<>();
        for (String stringRoles : role) {
            sr.add(roleService.getRoleByName(stringRoles));
        }
        user.setRoles(sr);
        userService.save(user);
        return "redirect:/admin";
    }

    @GetMapping("/{id}/edit")
    public String upDate(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        return "users/edit";
    }

    @PutMapping("/{id}")
    public String updateUser(@ModelAttribute("user") User user,
                             @PathVariable("id") Long id,
                             @RequestParam("role") String[] role) {
        Set<Role> roles = new HashSet<>();
        for (String stringRoles : role) {
            roles.add(roleService.getRoleByName(stringRoles));
        }
        user.setRoles(roles);
        userService.editUser(user, id);
        return "redirect:/admin";
    }

    @DeleteMapping("/{id}/delete")
    public String deleteUser(User user, @PathVariable("id") Long id) {
        userService.deleteUser(user, id);
        return "redirect:/admin";
    }
}
