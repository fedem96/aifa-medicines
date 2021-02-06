package com.fedem96.rest;

import com.fedem96.controller.CompanyController;
import com.fedem96.controller.MedicineController;
import com.fedem96.controller.PrincipleController;
import com.fedem96.model.Company;
import com.fedem96.model.Medicine;
import com.fedem96.model.Principle;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("search")
public class Searcher {

    @Inject
    private MedicineController medicineController;
    @Inject
    private PrincipleController principleController;
    @Inject
    private CompanyController companyController;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response search(@QueryParam("medicine") String medicine, @QueryParam("principle") String principle, @QueryParam("company") String company) {
        try {
            if(medicine != null && principle == null && company == null) {
                List<Medicine> medicines = medicineController.search(medicine);
                return Response.ok(medicines).build();
            } else if(medicine == null && principle != null && company == null) {
                List<Principle> principles = principleController.search(principle);
                return Response.ok(principles).build();
            } else if(medicine == null && principle == null && company != null) {
                List<Company> companies = companyController.search(company);
                return Response.ok(companies).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Response.serverError().build();
    }
}