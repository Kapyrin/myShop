package kapyrin.myshop.servlet.util;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kapyrin.myshop.dao.impl.UserDAOImpl;
import kapyrin.myshop.entity.ShopOrder;
import kapyrin.myshop.entity.User;
import kapyrin.myshop.service.impl.UserServiceImpl;
import kapyrin.myshop.servlet.util.ReportWriter;
import kapyrin.myshop.servlet.util.ReportStringGenerator;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet("/report")
public class ReportServlet extends HttpServlet {
    private UserServiceImpl userService;

    @Override
    public void init() {
        userService = UserServiceImpl.INSTANCE.initRepository(UserDAOImpl.INSTANCE);
        ReportWriter.INSTANCE.setServletContext(getServletContext());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String toTxt = request.getParameter("reportType");
        HttpSession session = request.getSession();
        String reportContent;
        String fileName;

        switch (toTxt) {
            case "usersOrders":
                List<User> onlyCustomers = (List<User>) session.getAttribute("users");
                Map<Long, List<ShopOrder>> userOrders = (Map<Long, List<ShopOrder>>) session.getAttribute("userOrders");

                if (onlyCustomers == null || userOrders == null) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Wrong request");
                    return;
                }

                reportContent = ReportStringGenerator.INSTANCE.fromUsersOrders(onlyCustomers, userOrders);
                fileName = "userOrdersReport";
                break;

            case "allUsers":
                List<User> users = (List<User>) session.getAttribute("users");

                if (users == null) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "No users available.");
                    return;
                }

                reportContent = ReportStringGenerator.INSTANCE.fromUserList(users);
                fileName = "userReport";
                break;

            case "userOrders":
                List<ShopOrder> orders = (List<ShopOrder>) session.getAttribute("orders");

                if (orders == null || orders.isEmpty()) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "No orders available.");
                    return;
                }

                reportContent = ReportStringGenerator.INSTANCE.fromUserOrders(orders,session);
                fileName = "userPersonalOrdersReport";
                break;

            default:
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid report type.");
                return;
        }

        File reportFile = ReportWriter.INSTANCE.saveStringToFile(reportContent, fileName);
        String fileUrl = request.getContextPath() + "/report/" + reportFile.getName();
        response.sendRedirect(fileUrl);
    }
}
