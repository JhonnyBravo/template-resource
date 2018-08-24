package template_resource;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import status_resource.StatusController;

/**
 * ファイルをコピーする。
 */
public class CopyFile extends StatusController {
    private String path;
    private String source;

    /**
     * @param source コピー元ファイルのパスを指定する。
     * @param path   コピー先のパスを指定する。
     */
    public CopyFile(String source, String path) {
        this.path = path;
        this.source = source;
    }

    /**
     * ファイルをコピーする。
     */
    public void runCommand() {
        this.initStatus();

        // コピー元ファイルが存在しない場合
        if (new File(this.source).isFile() == false) {
            this.setMessage(this.source + " が見つかりません。");
            this.errorTerminate();
            return;
        }

        if (new File(this.path).isDirectory()) {
            // コピー先がディレクトリである場合
            this.setMessage(this.path + " はディレクトリです。");
            this.errorTerminate();
            return;
        } else {
            // 上記以外の場合
            try {
                Files.copy(new File(this.source).toPath(), new File(this.path).toPath(),
                        StandardCopyOption.REPLACE_EXISTING);
                this.setCode(2);
                this.setMessage(null);
            } catch (IOException e) {
                this.setMessage("エラーが発生しました。" + e.toString());
                this.errorTerminate();
            }
        }
    }
}
