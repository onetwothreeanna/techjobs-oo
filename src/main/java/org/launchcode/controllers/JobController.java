package org.launchcode.controllers;

import org.launchcode.models.*;
import org.launchcode.models.data.JobFieldData;
import org.launchcode.models.forms.JobForm;
import org.launchcode.models.data.JobData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.ArrayList;

/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping(value = "job")
public class JobController {

    private JobData jobData = JobData.getInstance();

    // The detail display for a given Job at URLs like /job?id=17
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(Model model, int id) {

        // TODO #1 - get the Job with the given ID and pass it into the view
        Job job = jobData.findById(id);
        model.addAttribute(job);
        return "job-detail";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute(new JobForm());
        return "new-job";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add(Model model, @Valid JobForm jobForm, Errors errors) {

        // TODO #6 - Validate the JobForm model, and if valid, create a
        // new Job and add it to the jobData data store. Then
        // redirect to the job detail view for the new Job.
        if (errors.hasErrors()) {
            return "new-job";
        }

        Job newJob = new Job();
        jobData.add(newJob); 

        //getIds
        int employerId = jobForm.getEmployerId();
        int locationId = jobForm.getLocationId();
        int positionTypeId = jobForm.getPositionTypeId();
        int coreCompetencyId= jobForm.getCoreCompetencyId();

        //get Lists
        JobFieldData<Employer> Employers = jobData.getEmployers();
        JobFieldData<Location> Locations = jobData.getLocations();
        JobFieldData<PositionType> PositionTypes = jobData.getPositionTypes();
        JobFieldData<CoreCompetency> CoreCompetencies = jobData.getCoreCompetencies();

        //find objects in lists by ID
        Employer employerObject = Employers.findById(employerId);
        Location locationObject = Locations.findById(locationId);
        PositionType positionTypeObject = PositionTypes.findById(positionTypeId);
        CoreCompetency coreCompetencyObject = CoreCompetencies.findById(coreCompetencyId);

        //set'em
        newJob.setName(jobForm.getName());
        newJob.setEmployer(employerObject); //
        newJob.setLocation(locationObject);
        newJob.setPositionType(positionTypeObject);
        newJob.setCoreCompetency(coreCompetencyObject);


        return "redirect:/job?id=" + newJob.getId();
    }


}


