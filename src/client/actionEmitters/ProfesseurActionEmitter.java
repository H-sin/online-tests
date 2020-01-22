package client.actionEmitters;

import models.Etudiant;
import models.Professeur;
import models.Test;
import util.Action;
import util.Request;
import util.Response;
import util.Role;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class ProfesseurActionEmitter extends ActionEmitter {

    public ProfesseurActionEmitter(Socket socket) throws IOException {
        super(socket);
    }


    public Professeur login(Professeur prof) throws Exception {
        System.out.println("login professeur...");
        Request request = new Request(Action.LOGIN, prof, Role.PROFESSEUR);
        Response response = post(request);
        if(response == null){
            return null;
        }
        Professeur result;
        if (response.getStatus() == 0) {
            result = (Professeur) response.getData();
            System.out.println("Welcome " + result.getNom() + " " + result.getPrenom());
        } else {
            System.out.println(response.getMessage());
            throw new Exception(response.getMessage());
        }
        return result;
    }




}
