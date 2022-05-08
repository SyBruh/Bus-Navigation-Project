package com.example.projectbus;

class BankConnectionData {
    public String BankResponse;

    public BankConnectionData(String bankResponse) {
        BankResponse = bankResponse;
    }

    public  BankConnectionData()
    {

    }

    public String getBankResponse() {
        return BankResponse;
    }

    public void setBankResponse(String bankResponse) {
        BankResponse = bankResponse;
    }
}
