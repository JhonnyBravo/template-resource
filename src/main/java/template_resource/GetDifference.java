package template_resource;

import java.util.List;

import difflib.Delta;
import difflib.DiffUtils;
import difflib.Patch;
import status_resource.StatusController;

/**
 * ファイルの差分を取得する。
 */
public class GetDifference extends StatusController {
    private String difference;
    private String reference;

    /**
     * @param reference  比較元ファイルのパスを指定する。
     * @param difference 比較先ファイルのパスを指定する。
     */
    public GetDifference(String reference, String difference) {
        this.difference = difference;
        this.reference = reference;
    }

    /**
     * ファイルの差分を取得する。
     * 
     * @return List&lt;Delta&lt;String&gt;&gt;
     */
    public List<Delta<String>> runCommand() {
        this.initStatus();

        List<Delta<String>> deltaList = null;

        // 比較元ファイル
        GetContent gc = new GetContent(this.reference);
        List<String> referList = gc.runCommand();

        if (gc.getCode() == 1) {
            this.setCode(gc.getCode());
            return deltaList;
        }

        // 比較先ファイル
        gc = new GetContent(this.difference);
        List<String> differList = gc.runCommand();

        if (gc.getCode() == 1) {
            this.setCode(gc.getCode());
            return deltaList;
        }

        Patch<String> patch = DiffUtils.diff(referList, differList);
        deltaList = patch.getDeltas();

        if (deltaList.isEmpty()) {
            this.initStatus();
        } else {
            this.setCode(2);
            this.setMessage(null);
        }

        return deltaList;
    }

}
