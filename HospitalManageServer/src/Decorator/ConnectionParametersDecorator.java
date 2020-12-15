package Decorator;

 abstract  public class ConnectionParametersDecorator implements ConnectionParameters {
    private ConnectionParameters connectionParameters;

    public ConnectionParametersDecorator(ConnectionParameters connectionParameters){
        this.connectionParameters = connectionParameters;
    }
     @Override
     public String decorate() {
         return connectionParameters.decorate();
     }

}
