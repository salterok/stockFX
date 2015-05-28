package pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import constants.NavigationMethod;

/**
 * Created by salterok on 16.05.2015.
 */
public class NavigationDescriptor {
    public String id = null;
    public String title;
    public NavigationMethod method;
    public String value;
    public boolean isEnabled = true;
}
