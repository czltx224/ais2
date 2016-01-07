package gdcn.gui.resource;

import com.gdcn.Config;

import javax.swing.*;
import java.util.Properties;

/**
 * User: »Æº£êÌ
 * Date: 2007-11-27
 * Time: 13:08:08
 */
public class ImageRes {
    public static Properties properties = Config.getProperties();
    public static final ImageIcon icon = new ImageIcon(ImageRes.class.getResource(properties.getProperty("icon")));

    public static final ImageIcon imageUp = new ImageIcon(ImageRes.class.getResource(properties.getProperty("imageUp")));
    public static final ImageIcon imageDown = new ImageIcon(ImageRes.class.getResource(properties.getProperty("imageDown")));
    public static final ImageIcon imageBlank = new ImageIcon(ImageRes.class.getResource(properties.getProperty("imageBlank")));
    public static final ImageIcon save = new ImageIcon(ImageRes.class.getResource(properties.getProperty("save")));
    public static final ImageIcon refresh = new ImageIcon(ImageRes.class.getResource(properties.getProperty("refresh")));
    public static final ImageIcon undo = new ImageIcon(ImageRes.class.getResource(properties.getProperty("undo")));
    public static final ImageIcon branch = new ImageIcon(ImageRes.class.getResource(properties.getProperty("branch")));
    public static final ImageIcon profile = new ImageIcon(ImageRes.class.getResource(properties.getProperty("profile")));
    public static final ImageIcon userrolesetting = new ImageIcon(ImageRes.class.getResource(properties.getProperty("userrolesetting")));

    public static final ImageIcon function  = new ImageIcon(ImageRes.class.getResource(properties.getProperty("function")));
    public static final ImageIcon functionAdd = new ImageIcon(ImageRes.class.getResource(properties.getProperty("functionAdd")));
    public static final ImageIcon functionEdit = new ImageIcon(ImageRes.class.getResource(properties.getProperty("functionEdit")));
    public static final ImageIcon functionDelete = new ImageIcon(ImageRes.class.getResource(properties.getProperty("functionDelete")));
    
    public static final ImageIcon user  = new ImageIcon(ImageRes.class.getResource(properties.getProperty("user")));
    public static final ImageIcon userAdd = new ImageIcon(ImageRes.class.getResource(properties.getProperty("userAdd")));
    public static final ImageIcon userEdit = new ImageIcon(ImageRes.class.getResource(properties.getProperty("userEdit")));
    public static final ImageIcon userDelete = new ImageIcon(ImageRes.class.getResource(properties.getProperty("userDelete")));

    public static final ImageIcon group  = new ImageIcon(ImageRes.class.getResource(properties.getProperty("group")));
    public static final ImageIcon groupAdd = new ImageIcon(ImageRes.class.getResource(properties.getProperty("groupAdd")));
    public static final ImageIcon groupEdit = new ImageIcon(ImageRes.class.getResource(properties.getProperty("groupEdit")));
    public static final ImageIcon groupDelete = new ImageIcon(ImageRes.class.getResource(properties.getProperty("groupDelete")));

    public static final ImageIcon role  = new ImageIcon(ImageRes.class.getResource(properties.getProperty("role")));
    public static final ImageIcon roleAdd = new ImageIcon(ImageRes.class.getResource(properties.getProperty("roleAdd")));
    public static final ImageIcon roleEdit = new ImageIcon(ImageRes.class.getResource(properties.getProperty("roleEdit")));
    public static final ImageIcon roleDelete = new ImageIcon(ImageRes.class.getResource(properties.getProperty("roleDelete")));
    public static final ImageIcon up = new ImageIcon(ImageRes.class.getResource(properties.getProperty("up")));
    public static final ImageIcon down = new ImageIcon(ImageRes.class.getResource(properties.getProperty("down")));
    public static final ImageIcon search = new ImageIcon(ImageRes.class.getResource(properties.getProperty("search")));
}
