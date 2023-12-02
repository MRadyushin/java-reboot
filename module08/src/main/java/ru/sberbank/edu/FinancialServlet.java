package ru.sberbank.edu;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class FinancialServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>Калькулятор доходности вклада</h1>");
        out.println("<form action='/module08-1.0-SNAPSHOT/finance' method='post'>");
        out.println("<p>Сумма на момент открытия <input type='text' name='sum' class=\"colortext\"></p>" +
                "<style>.colortext { background-color: #cfd1cd;  } </style>");
        out.println("<p>Годовая процентная ставка  <input type='text' name='percentage' class=\"colortext\"></p>" +
                "<style>.colortext { background-color: #cfd1cd;  } </style>");
        out.println("<p>Количество лет <input type='text' name='years' class=\"colortext\"></p>" +
                "<style>.colortext { background-color: #cfd1cd;  } </style>");
        out.println("<input type='submit' value='Посчитать'  style=\"background-color: #1e90ff\">");
        out.println("</form>");
        out.println("</body></html>");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        try {
            double sum = Double.parseDouble(request.getParameter("sum"));
            double percentage = Double.parseDouble(request.getParameter("percentage"));
            int years = Integer.parseInt(request.getParameter("years"));
            if (sum < 0 || years < 0 || percentage < 0) {
                out.println("<html><body>");
                out.println("<h3>Неверный формат данных. Скорректируйте значения</h3>");
                out.println("</body></html>");
            } else if (sum < 50000) {
                out.println("<html><body>");
                out.println("<h3>Минимальная сумма на момент открытия вклада 50 000 рублей</h3>");
                out.println("</body></html>");
            } else {
                double finalAmount = (double) Math.round((sum * Math.pow(1 + (percentage / 100), years)) * 100) / 100;
                out.println("<html><body>");
                out.println("<h1>Результат</h1>");
                out.println("<p>Итоговая сумма " + finalAmount + "</p>");
                out.println("</body></html>");
            }
        } catch (NumberFormatException e) {
            out.println("<html><body>");
            out.println("<h3>Неверный формат данных. Скорректируйте значения</h3>");
            out.println("</body></html>");
        } finally {
            out.close();
        }
    }
}