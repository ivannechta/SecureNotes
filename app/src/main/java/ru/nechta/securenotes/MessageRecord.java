package ru.nechta.securenotes;

public class MessageRecord {
    public int id;
    public int Icon;
    public String Caption;
    public String Message;

    public MessageRecord(int i,int ico,String a,String b){
        id=i;
        Icon=ico;
        Caption=a;
        Message=b;
    }
}
