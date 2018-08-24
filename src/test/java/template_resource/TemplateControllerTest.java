package template_resource;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

/**
 * {@link template_resource.TemplateController} の単体テスト。
 */
public class TemplateControllerTest {

    /**
     * <p>
     * {@link template_resource.TemplateController#create()} のためのテスト・メソッド。
     * </p>
     * 
     * <p>
     * 確認事項
     * </p>
     * 
     * <ul>
     * <li>コピー元ファイルが存在しない場合にエラーとなること。</li>
     * <li>コピー先パスへファイルがコピーされないこと。</li>
     * </ul>
     */
    @Test
    public void test1() {
        TemplateController tc = new TemplateController("src/test/resources/NotExist.txt", "NotExist.txt");
        tc.create();

        assertEquals(1, tc.getCode());
        assertFalse(new File("NotExist.txt").isFile());
    }

    /**
     * <p>
     * {@link template_resource.TemplateController#create()} のためのテスト・メソッド。
     * </p>
     * 
     * <p>
     * 確認事項
     * </p>
     * 
     * <ul>
     * <li>コピー元ファイルが存在しない場合にエラーとなること。</li>
     * </ul>
     */
    @Test
    public void test2() {
        File f = new File("test.txt");

        try {
            f.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        TemplateController tc = new TemplateController("src/test/resources/NotExist.txt", "test.txt");
        tc.create();

        assertEquals(1, tc.getCode());

        f.delete();
    }

    /**
     * <p>
     * {@link template_resource.TemplateController#create()} のためのテスト・メソッド。
     * </p>
     * 
     * <p>
     * 確認事項
     * </p>
     * 
     * <ul>
     * <li>コピー元ファイルがコピー先パスへコピーされること。</li>
     * <li>ファイルの内容に追加・変更・削除が発生した場合に差分が出力されること。</li>
     * </ul>
     */
    @Test
    public void test3() {
        System.out.println("新規作成。");
        TemplateController tc = new TemplateController("src/test/resources/test1.txt", "test.txt");
        tc.create();
        assertEquals(2, tc.getCode());
        assertTrue(new File("test.txt").isFile());

        System.out.println("変更なし。");
        tc.create();
        assertEquals(0, tc.getCode());

        System.out.println("追加あり。");
        tc = new TemplateController("src/test/resources/test2.txt", "test.txt");
        tc.create();
        assertEquals(2, tc.getCode());

        System.out.println("変更あり。");
        tc = new TemplateController("src/test/resources/test3.txt", "test.txt");
        tc.create();
        assertEquals(2, tc.getCode());

        System.out.println("削除あり。");
        tc = new TemplateController("src/test/resources/test1.txt", "test.txt");
        tc.create();
        assertEquals(2, tc.getCode());

        new File("test.txt").delete();
    }

    /**
     * <p>
     * {@link template_resource.TemplateController#create()} のためのテスト・メソッド。
     * </p>
     * 
     * <p>
     * 確認事項
     * </p>
     * 
     * <ul>
     * <li>コピー先パスがディレクトリである場合にエラーとなること。</li>
     * </ul>
     */
    @Test
    public void test4() {
        File f = new File("test_dir");
        f.mkdir();

        TemplateController tc = new TemplateController("src/test/resources/test1.txt", "test_dir");
        tc.create();

        assertEquals(1, tc.getCode());
        f.delete();
    }
}
