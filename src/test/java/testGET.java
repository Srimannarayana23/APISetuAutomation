
import Helpers.APIHelper;
import Model.*;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
//import java.util.Date;
//import java.util.List;
import java.util.*;
import static org.testng.Assert.*;

public class testGET {
    private APIHelper apihelper;
    private StateResponse stateResponse;
    private int stateId;
    private int districtId;

    @BeforeClass
    public void init() {
        apihelper = new APIHelper();
        stateResponse = apihelper.getAllStates();

    }


    /*
    Validates that the state ID of Karnataka is 16
     */
    @Test
    public void validateStateID(){
        assertNotNull(stateResponse, "GET States returned non-empty list");
        List<State> states = stateResponse.states;
        boolean found = false;
        for (State s : states) {
            if (s.state_name.equals("Karnataka")) {
                found = true;
                assertEquals(s.state_id, 16);
                return;
            }
        }
        assertNotEquals(found, false, "State not found");
    }

    /*
    Validates that the district ID of Bengaluru is 265
     */
    @Test
    public void validateDistrictId(){
        DistrictResponse districtResponse = APIHelper.getDistricts(16);
        assertNotNull(districtResponse, "GET Districts returned empty list");
        List<District> districts = districtResponse.districts;
        boolean found = false;
        for (District d: districts) {
            if (d.district_name.equals("Bangalore Urban")) {
                found = true;
                assertEquals(d.district_id, 265, "Ok");
                return;
            }
        }
        assertNotEquals(found, false, "District not found");
    }

    /*
    Validates that every state and UT has an ID
     */
    @Test
    public void testGetStates(){
        assertNotNull(stateResponse, "GET States returned non-empty list");
        List<State> states = stateResponse.states;
        for (State s : states) {
            assertNotNull(s.state_id, "All States and Union Territories should have their unique ids");
        }
    }

    /*
     Validates that Springleaf's vaccine fees > Rs 300
    */
    @Test
    public void testSpringleafHealthcare(){
        HospitalResponse hospitalsResponse = APIHelper.getHospitals(265, new Date());
        assertNotNull(hospitalsResponse, "GET Hospitals returned non-empty list");
        List<Session> sessions = hospitalsResponse.sessions;
        boolean found = false;
        for (Session session : sessions) {
            String name = session.name;
            double fee = Double.parseDouble(session.fee);
            if (name.equals("Springleaf Healthcare")) {
                found = true;
                assertTrue(fee > 300.0);
            }
        }
        assertNotEquals(found, false, "Hospital not found");
    }


    // Validates that there exists at least 1 free vaccine center

    @Test
    public void testFreeCenters(){
        List<State> states = stateResponse.states;
        boolean atLeastOneFree = false;
        for (State state : states) {
            DistrictResponse districtResponse = APIHelper.getDistricts(state.state_id);
            List<District> districts = districtResponse.districts;
            for (District district : districts) {
                HospitalResponse hospitalsResponse = APIHelper.getHospitals(district.district_id, new Date());
                List<Session> sessions = hospitalsResponse.sessions;
                for (Session session : sessions) {
                    String fee_type = session.fee_type;
                    double fee = Double.parseDouble(session.fee);
                    if (fee_type.equals("Free") || fee == 0.0) {
                        atLeastOneFree = true;
                        assertTrue(atLeastOneFree == true);
                        return;
                    }

                }
            }
        }
        assertNotEquals(false, false, "No hospital found that provides free vaccination");
    }
// Find that name of hospital giving Covaxin dosage which is paid.
//        (District Bangalore Urban, Karnataka, Date - today)
    @Test
    public void FindPaidVaccineHsptl(){
        String nameofhsptl = "";
        HospitalResponse hsptlRes = APIHelper.getHospitals(265,new Date());
        assertNotNull(hsptlRes, "GET Hospitals returned non-empty list");
        List<Session> sessions = hsptlRes.sessions;
        boolean found=false;
        for (Session session : sessions) {
            String fee = session.fee;
            String Vaccine = session.vaccine;
            double Fee = Double.parseDouble(session.fee);
            if (Fee > 0 && Vaccine.equalsIgnoreCase("Covaxin")) {
                found = true;
                nameofhsptl = session.name;
                System.out.printf("%s  ->  %s -> %.2f\n", nameofhsptl, Vaccine ,Fee);
//                return;
            }
        }
        assertNotEquals(found,false,"No Hospitals found providing covaxin which are paid");

    }






}