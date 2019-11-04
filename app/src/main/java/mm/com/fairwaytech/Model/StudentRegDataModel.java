package mm.com.fairwaytech.Model;

public class StudentRegDataModel {
/*
String attend_class,student_information, current_job, want_job, expert_technology, register_date;

    public StudentRegDataModel(String attend_class, String student_information, String current_job, String want_job, String expert_technology, String register_date) {
        this.attend_class = attend_class;
        this.student_information = student_information;
        this.current_job = current_job;
        this.want_job = want_job;
        this.expert_technology = expert_technology;
        this.register_date = register_date;
    }

    public String getAttend_class() {
        return attend_class;
    }

    public void setAttend_class(String attend_class) {
        this.attend_class = attend_class;
    }

    public String getStudent_information() {
        return student_information;
    }

    public void setStudent_information(String student_information) {
        this.student_information = student_information;
    }

    public String getCurrent_job() {
        return current_job;
    }

    public void setCurrent_job(String current_job) {
        this.current_job = current_job;
    }

    public String getWant_job() {
        return want_job;
    }

    public void setWant_job(String want_job) {
        this.want_job = want_job;
    }

    public String getExpert_technology() {
        return expert_technology;
    }

    public void setExpert_technology(String expert_technology) {
        this.expert_technology = expert_technology;
    }

    public String getRegister_date() {
        return register_date;
    }

    public void setRegister_date(String register_date) {
        this.register_date = register_date;
    }
 */
    private String attend_class,name, email, phone, address, cur_web_dev, cur_app_dev, cur_mobile_dev, cur_designer, cur_student, cur_other, want_web_dev, want_software_dev, want_mobile_dev, want_backend_dev, want_frontend_dev, want_graphic, want_web_designer, want_uiux_designer, expert_html, expert_javascript, expert_php, expert_ruby, expert_csharp, expert_java, expert_basic_design, expert_drawing, expert_photography, expert_print, expert_flim, expert_illustrator, expert_other, register_date;

    // State of the item
    private boolean expanded;

    public StudentRegDataModel(String attend_class, String name, String email, String phone, String address, String cur_web_dev, String cur_app_dev, String cur_mobile_dev, String cur_designer, String cur_student, String cur_other, String want_web_dev, String want_software_dev, String want_mobile_dev, String want_backend_dev, String want_frontend_dev, String want_graphic, String want_web_designer, String want_uiux_designer, String expert_html, String expert_javascript, String expert_php, String expert_ruby, String exptrt_csharp, String expert_java, String expert_basic_design, String expert_drawing, String expert_photography, String expert_print, String expert_flim, String expert_illustrator, String expert_other, String register_date) {
        this.attend_class = attend_class;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.cur_web_dev = cur_web_dev;
        this.cur_app_dev = cur_app_dev;
        this.cur_mobile_dev = cur_mobile_dev;
        this.cur_designer = cur_designer;
        this.cur_student = cur_student;
        this.cur_other = cur_other;
        this.want_web_dev = want_web_dev;
        this.want_software_dev = want_software_dev;
        this.want_mobile_dev = want_mobile_dev;
        this.want_backend_dev = want_backend_dev;
        this.want_frontend_dev = want_frontend_dev;
        this.want_graphic = want_graphic;
        this.want_web_designer = want_web_designer;
        this.want_uiux_designer = want_uiux_designer;
        this.expert_html = expert_html;
        this.expert_javascript = expert_javascript;
        this.expert_php = expert_php;
        this.expert_ruby = expert_ruby;
        this.expert_csharp = exptrt_csharp;
        this.expert_java = expert_java;
        this.expert_basic_design = expert_basic_design;
        this.expert_drawing = expert_drawing;
        this.expert_photography = expert_photography;
        this.expert_print = expert_print;
        this.expert_flim = expert_flim;
        this.expert_illustrator = expert_illustrator;
        this.expert_other = expert_other;
        this.register_date = register_date;

    }

    public String getAttend_class() {
        return attend_class;
    }

    public void setAttend_class(String attend_class) {
        this.attend_class = attend_class;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCur_web_dev() {
        return cur_web_dev;
    }

    public void setCur_web_dev(String cur_web_dev) {
        this.cur_web_dev = cur_web_dev;
    }

    public String getCur_app_dev() {
        return cur_app_dev;
    }

    public void setCur_app_dev(String cur_app_dev) {
        this.cur_app_dev = cur_app_dev;
    }

    public String getCur_mobile_dev() {
        return cur_mobile_dev;
    }

    public void setCur_mobile_dev(String cur_mobile_dev) {
        this.cur_mobile_dev = cur_mobile_dev;
    }

    public String getCur_designer() {
        return cur_designer;
    }

    public void setCur_designer(String cur_designer) {
        this.cur_designer = cur_designer;
    }

    public String getCur_student() {
        return cur_student;
    }

    public void setCur_student(String cur_student) {
        this.cur_student = cur_student;
    }

    public String getCur_other() {
        return cur_other;
    }

    public void setCur_other(String cur_other) {
        this.cur_other = cur_other;
    }

    public String getWant_web_dev() {
        return want_web_dev;
    }

    public void setWant_web_dev(String want_web_dev) {
        this.want_web_dev = want_web_dev;
    }

    public String getWant_software_dev() {
        return want_software_dev;
    }

    public void setWant_software_dev(String want_software_dev) {
        this.want_software_dev = want_software_dev;
    }

    public String getWant_mobile_dev() {
        return want_mobile_dev;
    }

    public void setWant_mobile_dev(String want_mobile_dev) {
        this.want_mobile_dev = want_mobile_dev;
    }

    public String getWant_backend_dev() {
        return want_backend_dev;
    }

    public void setWant_backend_dev(String want_backend_dev) {
        this.want_backend_dev = want_backend_dev;
    }

    public String getWant_frontend_dev() {
        return want_frontend_dev;
    }

    public void setWant_frontend_dev(String want_frontend_dev) {
        this.want_frontend_dev = want_frontend_dev;
    }

    public String getWant_graphic() {
        return want_graphic;
    }

    public void setWant_graphic(String want_graphic) {
        this.want_graphic = want_graphic;
    }

    public String getWant_web_designer() {
        return want_web_designer;
    }

    public void setWant_web_designer(String want_web_designer) {
        this.want_web_designer = want_web_designer;
    }

    public String getWant_uiux_designer() {
        return want_uiux_designer;
    }

    public void setWant_uiux_designer(String want_uiux_designer) {
        this.want_uiux_designer = want_uiux_designer;
    }

    public String getExpert_html() {
        return expert_html;
    }

    public void setExpert_html(String expert_html) {
        this.expert_html = expert_html;
    }

    public String getExpert_javascript() {
        return expert_javascript;
    }

    public void setExpert_javascript(String expert_javascript) {
        this.expert_javascript = expert_javascript;
    }

    public String getExpert_php() {
        return expert_php;
    }

    public void setExpert_php(String expert_php) {
        this.expert_php = expert_php;
    }

    public String getExpert_ruby() {
        return expert_ruby;
    }

    public void setExpert_ruby(String expert_ruby) {
        this.expert_ruby = expert_ruby;
    }

    public String getExpert_csharp() {
        return expert_csharp;
    }

    public void setExpert_csharp(String exptrt_csharp) {
        this.expert_csharp = exptrt_csharp;
    }

    public String getExpert_java() {
        return expert_java;
    }

    public void setExpert_java(String expert_java) {
        this.expert_java = expert_java;
    }

    public String getExpert_basic_design() {
        return expert_basic_design;
    }

    public void setExpert_basic_design(String expert_basic_design) {
        this.expert_basic_design = expert_basic_design;
    }

    public String getExpert_drawing() {
        return expert_drawing;
    }

    public void setExpert_drawing(String expert_drawing) {
        this.expert_drawing = expert_drawing;
    }

    public String getExpert_photography() {
        return expert_photography;
    }

    public void setExpert_photography(String expert_photography) {
        this.expert_photography = expert_photography;
    }

    public String getExpert_print() {
        return expert_print;
    }

    public void setExpert_print(String expert_print) {
        this.expert_print = expert_print;
    }

    public String getExpert_flim() {
        return expert_flim;
    }

    public void setExpert_flim(String expert_flim) {
        this.expert_flim = expert_flim;
    }

    public String getExpert_illustrator() {
        return expert_illustrator;
    }

    public void setExpert_illustrator(String expert_illustrator) {
        this.expert_illustrator = expert_illustrator;
    }

    public String getExpert_other() {
        return expert_other;
    }

    public void setExpert_other(String expert_other) {
        this.expert_other = expert_other;
    }

    public String getRegister_date() {
        return register_date;
    }

    public void setRegister_date(String register_date) {
        this.register_date = register_date;
    }


    // state of item
    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    public boolean isExpanded(){
        return expanded;
    }
}
