package jp.dip.gpsoft.mdpubw;

import static java.util.stream.Stream.concat;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.stream.Stream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.dip.gpsoft.mdpub.Application;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@SuppressWarnings("serial")
@WebServlet(
    description = "Pull markdowns from the repository and publish them.",
    urlPatterns = {"/publish"})
public class Publish extends HttpServlet {
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException,
            IOException {
        Logger logger = LogManager.getLogger();
        logger.info("Publish servlet got a post request.");

        String inDir = request.getParameter("IN-dir");
        String outDir = request.getParameter("OUT-dir");
        String[] subDirs = request.getParameterValues("subDirs");
        String[] args =
            concat(concat(Stream.of(inDir), Stream.of(subDirs)),
                    Stream.of(outDir)).toArray(String[]::new);

        if (request.getParameter("pull") != null) pull(inDir);

        logger.debug("calling mdpub {}", Arrays.toString(args));
        Application.main(args);

        String bookUrl = request.getParameter("bookUrl");
        logger.info("Publish servlet is redirecting: {}", bookUrl);
        response.sendRedirect(bookUrl);
    }

    private void pull(String workingDir) {
        Logger logger = LogManager.getLogger();
        logger.info("Pulling from repository.");

        String pullCommands =
            getServletContext().getInitParameter("pullCommands");
        Stream.of(pullCommands.split(" *; *")).forEach(
                cmd -> execCommand(cmd, workingDir));
    }

    private void
            execCommand(String commandLine, String workingDir) {
        Logger logger = LogManager.getLogger();
        logger.info(commandLine);

        ProcessBuilder pb =
            new ProcessBuilder(commandLine.split(" +"));
        pb.directory(new File(workingDir));
        pb.redirectErrorStream(true);
        // stdoutとstderrを吸ってやらないとブロックする可能性あり。
        try {
            Process p = pb.start();
            try (BufferedReader br =
                new BufferedReader(new InputStreamReader(
                        p.getInputStream()))) {
                br.lines().forEach(logger::info);
            }
        } catch (IOException e) {
            logger.error("Failed to execute command: {}",
                    commandLine);
            logger.error(e);
            // 問題が起きても処理続行。
        }
    }
}
