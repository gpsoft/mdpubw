package jp.dip.gpsoft.mdpubw;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
@WebServlet(description = "Render the coverpage",
    urlPatterns = {"/coverpage"})
public class CoverPage extends HttpServlet {
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException,
            IOException {
        String key = request.getParameter("key");
        String title = initParam(key + "Title");
        Path inDir = Paths.get(initParam("mdpubHomeDir"), key);
        String[] subDirs = initParam(key + "SubDirs").split(", ?");
        Path outDir = Paths.get(initParam("mdpubOutDir"), key);
        String bookUrl = initParam("bookshelfUrl") + key;
        String imgUrl = bookUrl + "/" + initParam("jacketFile");

        request.setAttribute("title", title);
        request.setAttribute("inDir", inDir.toString());
        request.setAttribute("outDir", outDir.toString());
        request.setAttribute("subDirs", subDirs);
        request.setAttribute("bookUrl", bookUrl);
        request.setAttribute("imgUrl", imgUrl);

        request.getRequestDispatcher("/coverpage.jsp").forward(
                request, response);
    }

    private String initParam(String name) {
        return getServletContext().getInitParameter(name);
    }
}
