package template_resource;

import java.io.File;
import java.util.List;

import difflib.Delta;
import group_resource.SetGroupPrincipal;
import mode_resource.SetPermission;
import owner_resource.SetUserPrincipal;
import status_resource.StatusController;

/**
 * ファイルをコピーし、差分を表示する。
 */
public class TemplateController extends StatusController {
    private String path;
    private String source;

    /**
     * @param source コピー元ファイルのパスを指定する。
     * @param path   コピー先ファイルのパスを指定する。
     */
    public TemplateController(String source, String path) {
        this.source = source;
        this.path = path;
    }

    /**
     * ファイルをコピーし、差分を出力する。
     */
    public void create() {
        this.initStatus();

        CopyFile cf = new CopyFile(this.source, this.path);

        if (new File(this.path).isFile() == false) {
            cf.runCommand();
            this.setCode(cf.getCode());

            if (cf.getCode() == 1) {
                return;
            }

            GetContent gc = new GetContent(this.path);
            List<String> contentList = gc.runCommand();

            if (gc.getCode() == 1) {
                return;
            }

            for (String content : contentList) {
                System.out.println("+ " + content);
            }
        } else {
            GetDifference gd = new GetDifference(this.path, this.source);
            List<Delta<String>> deltaList = gd.runCommand();

            if (gd.getCode() != 2) {
                this.setCode(gd.getCode());
                return;
            }

            for (Delta<String> delta : deltaList) {
                if (delta.getType().equals(Delta.TYPE.CHANGE)) {
                    for (String line : delta.getOriginal().getLines()) {
                        System.out.println("- " + line);
                    }

                    for (String line : delta.getRevised().getLines()) {
                        System.out.println("+ " + line);
                    }
                } else if (delta.getType().equals(Delta.TYPE.DELETE)) {
                    for (String line : delta.getOriginal().getLines()) {
                        System.out.println("- " + line);
                    }
                } else if (delta.getType().equals(Delta.TYPE.INSERT)) {
                    for (String line : delta.getRevised().getLines()) {
                        System.out.println("+ " + line);
                    }
                }
            }

            cf.runCommand();
            this.setCode(cf.getCode());

            if (cf.getCode() == 1) {
                return;
            }
        }
    }

    /**
     * ファイル所有者を変更する。
     * 
     * @param owner 新しい所有者として設定するユーザの名前を指定する。
     */
    public void setOwner(String owner) {
        SetUserPrincipal sup = new SetUserPrincipal(this.path, owner);

        if (new File(this.path).isFile()) {
            sup.runCommand();
        } else {
            sup.initStatus();
        }

        this.setCode(sup.getCode());
    }

    /**
     * ファイルのグループ所有者を変更する。
     * 
     * @param group 新しいグループ所有者として設定するグループの名前を指定する。
     */
    public void setGroup(String group) {
        SetGroupPrincipal sgp = new SetGroupPrincipal(this.path, group);

        if (new File(this.path).isFile()) {
            sgp.runCommand();
        } else {
            sgp.initStatus();
        }

        this.setCode(sgp.getCode());
    }

    /**
     * ファイルパーミッションを変更する。
     * 
     * @param mode 新しく設定するパーミッションを 3 桁の数列で指定する。
     *             <ul>
     *             <li>0: ---</li>
     *             <li>1: --x</li>
     *             <li>2: -w-</li>
     *             <li>3: -wx</li>
     *             <li>4: r--</li>
     *             <li>5: r-x</li>
     *             <li>6: rw-</li>
     *             <li>7: rwx</li>
     *             </ul>
     */
    public void setMode(String mode) {
        SetPermission sp = new SetPermission(this.path, mode);

        if (new File(this.path).isFile()) {
            sp.runCommand();
        } else {
            sp.initStatus();
        }

        this.setCode(sp.getCode());
    }

}
