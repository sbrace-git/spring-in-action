package tacos;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(Model model) {
        // IDEA : CTRL + F9
        // chrome LiveReload 插件安装完成后，需要在浏览器点击启动 Enable LiveReload
        model.addAttribute("name", "value");
        return "home";
    }
}
