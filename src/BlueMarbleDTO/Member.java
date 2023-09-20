package BlueMarbleDTO;

public class Member {
    private int Num;
    private String Id;
    private String Pw;
    private String Name;
    private int budget;

    public int getNum() {
        return Num;
    }

    public void setNum(int num) {
        Num = num;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getPw() {
        return Pw;
    }

    public void setPw(String pw) {
        Pw = pw;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getBudget() {
        return budget;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }

    @Override
    public String toString() {
        return "Member{" +
                "Num=" + Num +
                ", Id='" + Id + '\'' +
                ", Pw='" + Pw + '\'' +
                ", Name='" + Name + '\'' +
                ", budget=" + budget +
                '}';
    }
}
