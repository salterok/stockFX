package pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javafx.scene.layout.Pane;
import jdk.nashorn.internal.ir.annotations.Ignore;
import views.BaseNavigableView;

/**
 * Created by salterok on 10.05.2015.
 */
public class ProgressiveViewItem {
    public String id;
    public String ref;
    public String title;
    @JsonIgnore
    public BaseNavigableView instance;
    public NavigationDescriptor[] navs;
}
