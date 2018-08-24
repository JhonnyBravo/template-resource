package template_resource;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.List;

import status_resource.StatusController;

/**
 * ファイルを読込み、 List&lt;String&gt; へ変換して返す。
 */
public class GetContent extends StatusController {
    private String path;

    /**
     * @param path 読込対象とするファイルのパスを指定する。
     */
    public GetContent(String path) {
        this.path = path;
    }

    /**
     * ファイルを読込み、 List&lt;String&gt; へ変換して返す。
     * 
     * @return List&lt;String&gt;
     */
    public List<String> runCommand() {
        this.initStatus();

        List<String> content = null;

        if (new File(this.path).isFile() == false) {
            this.setMessage(this.path + " が見つかりません。");
            this.errorTerminate();
            return content;
        }

        if (Files.isRegularFile(new File(this.path).toPath()) == false) {
            this.setMessage(this.path + " は通常ファイルではありません。");
            this.errorTerminate();
            return content;
        }

        try {
            content = Files.readAllLines(new File(this.path).toPath(), Charset.defaultCharset());
            this.setCode(2);
            this.setMessage(null);
        } catch (IOException e) {
            this.setMessage("エラーが発生しました。" + e.toString());
            this.errorTerminate();
        }

        return content;
    }
}
