package kapyrin.myshop.servlets;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Locale;

@WebServlet("/language")
public class LanguageSwitcherServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String lang = req.getParameter("lang");
        HttpSession session = req.getSession();

        if (lang != null) {
            Locale locale;
            switch (lang) {
                case "en":
                    locale = new Locale("en");
                    break;
                case "ru":
                    locale = new Locale("ru");
                    break;
                default:
                    locale = Locale.getDefault();
            }
        }
        session.setAttribute("lang", lang);

        String referer = req.getHeader("referer");
        if (referer != null) {
            resp.sendRedirect(referer);
        } else {
            resp.sendRedirect("index.jsp");
        }
    }
}
