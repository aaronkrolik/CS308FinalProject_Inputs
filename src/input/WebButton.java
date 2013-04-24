package input;

/**
 * A Web Button Data Container Object which is created
 * as the given resource files are interpreted so as to
 * intelligently combine up and down behaviors intuitively.
 * All fields are initialized as "" and are set through methods.
 * @author Gavin Ovsak
 *
 */
class WebButton {
    private String downBehavior = "";
    private String upBehavior = "";
    private String downInputBehaviors = "";
    private String upInputBehaviors = "";
    private String buttonType = "";
    
    public String getDownBehavior() {
        return downBehavior;
    }

    public String getUpBehavior() {
        return upBehavior;
    }
    
    public void setDownBehavior(String behavior) {
        downBehavior = behavior;
    }
    
    public void setUpBehavior(String behavior) {
        upBehavior = behavior;
    }

    public String getDownInputBehaviors() {
        return downInputBehaviors;
    }

    public String getUpInputBehaviors() {
        return upInputBehaviors;
    }
    
    public void setDownInputBehaviors(String behavior) {
        downInputBehaviors = behavior;
    }
    
    public void setUpInputBehaviors(String behavior) {
        upInputBehaviors = behavior;
    }
    
    public void setButtonType(String type) {
        buttonType = type;
    }
    
    public String getButtonType() {
        return buttonType;
    }
}

