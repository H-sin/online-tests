package util;

public enum Action {
    //login
        LOGIN,
    //exit
        EXIT,
        DEFAULT,
    //etudiant
        CREATE_ETUDIANT,
        DELETE_ETUDIANT,
        UPDATE_ETUDIANT,
        GET_ALL_ETUDIANTS,
        GET_ETUDIANT,
    GET_ETUDIANTS_GROUPE,
    //professeur
        CREATE_PROF,
        DELETE_PROF,
        UPDATE_PROF,
        GET_ALL_PROFS,
        GET_PROF,
    //Test
        ADD_TEST,
        ADD_TEST_2,
        UPDATE_TEST,
        DELETE_TEST,
        GET_NEW_TESTS_ETUDIANT,
        GET_OLD_TESTS_ETUDIANT,
        GET_ALL_TESTS_ETUDIANT,
        GET_TEST,
        GET_TESTS_PROFESSEUR,
        GET_FULL_TEST,
    //Fiche
        GET_FICHES_ETUDIANT,
        GET_FICHE_ETUDIANT,
        GET_FICHES_TEST,
        SUBMIT_FICHE,
    //Groupe
        GET_GROUPES,
    //Statitics
        GET_MOYENNES_GROUPES,
        GET_TESTS_MOYENNE

}
