package entity;

/**
 * @Author Legion
 * @Date 2020/12/21 15:12
 * @Description
 */
public class AccountInfo {
    private String account;
    private String password;
    private String personId;
    /**
     * true: teacher; false: student
     */
    private boolean personType;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public boolean isPersonType() {
        return personType;
    }

    public void setPersonType(boolean personType) {
        this.personType = personType;
    }


}
