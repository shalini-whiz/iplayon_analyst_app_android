package in.iplayon.analyst.modal;

public class TeamIDJson {

   // teamAId" : "hizYGsP2MjGaxsbLj",
       //     "teamBId" : "hWwjDwft4X4d7tmC8",
       //     "managerAId" : "3vvLgifFQYtXAKRMx",
       //     "managerBId" : "yNLWkXC5o3ZFcm2Yj"
    private String teamAId;
    private String teamBId;
    private String managerAId;
    private String managerBId;

    public String getTeamAId() {
        return teamAId;
    }

    public void setTeamAId(String teamAId) {
        this.teamAId = teamAId;
    }

    public String getTeamBId() {
        return teamBId;
    }

    public void setTeamBId(String teamBId) {
        this.teamBId = teamBId;
    }

    public String getManagerAId() {
        return managerAId;
    }

    public void setManagerAId(String managerAId) {
        this.managerAId = managerAId;
    }

    public String getManagerBId() {
        return managerBId;
    }

    public void setManagerBId(String managerBId) {
        this.managerBId = managerBId;
    }
}
