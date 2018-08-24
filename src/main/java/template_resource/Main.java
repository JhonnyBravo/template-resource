package template_resource;

import gnu.getopt.Getopt;
import gnu.getopt.LongOpt;

/**
 * ファイルをコピーし、差分を出力する。
 */
public class Main {
    /**
     * @param args
     *             <ul>
     *             <li>-s, --source &lt;path&gt; コピー元ファイルのパスを指定する。</li>
     *             <li>-p, --path &lt;path&gt; コピー先のパスを指定する。</li>
     *             <li>-o, --owner &lt;owner&gt; ファイル所有者を変更する。</li>
     *             <li>-g, --group &lt;group&gt; グループ所有者を変更する。</li>
     *             <li>-m, --mode &lt;mode&gt; ファイルパーミッションを変更する。</li>
     *             </ul>
     */
    public static void main(String[] args) {
        LongOpt[] longopts = new LongOpt[5];
        longopts[0] = new LongOpt("source", LongOpt.REQUIRED_ARGUMENT, null, 's');
        longopts[1] = new LongOpt("path", LongOpt.REQUIRED_ARGUMENT, null, 'p');
        longopts[2] = new LongOpt("owner", LongOpt.REQUIRED_ARGUMENT, null, 'o');
        longopts[3] = new LongOpt("group", LongOpt.REQUIRED_ARGUMENT, null, 'g');
        longopts[4] = new LongOpt("mode", LongOpt.REQUIRED_ARGUMENT, null, 'm');

        Getopt options = new Getopt("Main", args, "s:p:o:g:m:", longopts);

        int c;
        int sFlag = 0;
        int pFlag = 0;
        int oFlag = 0;
        int gFlag = 0;
        int mFlag = 0;

        String path = null;
        String source = null;
        String owner = null;
        String group = null;
        String mode = null;

        while ((c = options.getopt()) != -1) {
            switch (c) {
            case 's':
                source = options.getOptarg();
                sFlag = 1;
                break;
            case 'p':
                path = options.getOptarg();
                pFlag = 1;
                break;
            case 'o':
                owner = options.getOptarg();
                oFlag = 1;
                break;
            case 'g':
                group = options.getOptarg();
                gFlag = 1;
                break;
            case 'm':
                mode = options.getOptarg();
                mFlag = 1;
                break;
            }
        }

        TemplateController fc = new TemplateController(source, path);

        if (sFlag == 1 || pFlag == 1) {
            if (sFlag == 0) {
                System.err.println("source オプションを指定してください。");
                System.exit(1);
            } else if (pFlag == 0) {
                System.err.println("path オプションを指定してください。");
                System.exit(1);
            }
        }

        if (sFlag == 1 && pFlag == 1) {
            fc.create();

            if (fc.getCode() == 1) {
                System.exit(fc.getCode());
            }

            if (oFlag == 1) {
                fc.setOwner(owner);

                if (fc.getCode() == 1) {
                    System.exit(fc.getCode());
                }
            }

            if (gFlag == 1) {
                fc.setGroup(group);

                if (fc.getCode() == 1) {
                    System.exit(fc.getCode());
                }
            }

            if (mFlag == 1) {
                fc.setMode(mode);

                if (fc.getCode() == 1) {
                    System.exit(fc.getCode());
                }
            }
        }

        System.exit(fc.getCode());
    }

}
