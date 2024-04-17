package Napredno_Programiranje.LABS.LABS2.LABS2_1;

public class PhoneContact extends Contact{

    private final String phoneNumber;
    private final Operator operator;

    public PhoneContact(String date, String phone) {
        super(date);
        this.phoneNumber = phone;

        char op = phone.charAt(2);
        if (op == '0' || op == '1' || op == '2') {
            operator = Operator.TMOBILE;
        } else if (op == '5' || op == '6') {
            operator = Operator.ONE;
        } else {
            operator = Operator.VIP;
        }
    }

    public String getPhone() {
        return phoneNumber;
    }

    public Operator getOperator() {
        return operator;
    }
    @Override
    public String toString() {
        return "\"" + phoneNumber + "\"";
    }
    @Override
    public String getType() {
        return "Phone";
    }
}
//dobra e
