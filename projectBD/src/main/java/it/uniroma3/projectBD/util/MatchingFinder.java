package it.uniroma3.projectBD.util;

import it.uniroma3.projectBD.format.*;
import it.uniroma3.projectBD.value.*;
import it.uniroma3.projectBD.value.Date;
import it.uniroma3.projectBD.value.Number;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@Controller
public class MatchingFinder {

    /**
     * This REST method is concatenated with the findMatch() method. After insert the
     * required Threshold, automatically redirect to HTML matching list result page
     * created by execution of findMatch().
     * To include the Vectors as method inputs, modify the HTML page to redirect to
     * another REST service.
     * To obtain the original chained version, delete the vectors as findMatch input.
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String formThreshold() {

        return "formT";
    }

    /**
     * The model param is only use for HTML result page, to use this
     * REST service without HTML, remove the model and change the result return type.
     */
    @RequestMapping(value = "/application", method = RequestMethod.GET)
    public String findMatch(@RequestParam("threshold") String threshold,
                            Vector<Object> input_1, Vector<Object> input_2, Model model) {

        Map<Integer, List<Element>> mapResult = new HashMap<>();
        int t = 0;
        Double threshold_final = Double.parseDouble(threshold);
        TypeDecoder[] decoders = {Decoders.ISBN_DECODER, Decoders.NUMBER_DECODER,
                Decoders.PHONE_DECODER, Decoders.DATE_DECODER};
        UnionDecoder ud = new UnionDecoder(decoders);

        for (int i = 0; i < input_1.size(); i++) {
            for (int j = 0; j < input_2.size(); j++) {

                /* ISBN */
                if (ud.decode(input_1.get(i).toString()) instanceof ISBN &&
                        ud.decode(input_2.get(j).toString()) instanceof ISBN) {
                    if ((ValueDistances.ISBN_DISTANCE.distance(input_1.get(i).toString(),
                            input_2.get(j).toString())) <= threshold_final) {
                        List<Element> matchingList = new ArrayList<>();
                        matchingList.add(new Element("ISBN", input_1.get(i).toString()));
                        matchingList.add(new Element("ISBN", input_2.get(j).toString()));
                        mapResult.put(t, matchingList);
                        t = t + 1;
                    }
                }

                /* Phone */
                if (ud.decode(input_1.get(i).toString()) instanceof Phone &&
                        ud.decode(input_2.get(j).toString()) instanceof Phone) {
                    if ((ValueDistances.HAMMING.distance(input_1.get(i).toString(), input_2.get(j).toString())) <= threshold_final) {
                        List<Element> matchingList = new ArrayList<>();
                        matchingList.add(new Element("Phone", input_1.get(i).toString()));
                        matchingList.add(new Element("Phone", input_2.get(j).toString()));
                        mapResult.put(t, matchingList);
                        t = t + 1;
                    }
                }

                /* Date */
                if (ud.decode(input_1.get(i).toString()) instanceof Date &&
                        ud.decode(input_2.get(j).toString()) instanceof Date) {
                    if ((ValueDistances.DATE_DISTANCE.distance(input_1.get(i).toString(), input_2.get(j).toString())) <= threshold_final) {
                        List<Element> matchingList = new ArrayList<>();
                        matchingList.add(new Element("Date", input_1.get(i).toString()));
                        matchingList.add(new Element("Date", input_2.get(j).toString()));
                        mapResult.put(t, matchingList);
                        t = t + 1;
                    }
                }

                /* Number */
                if (ud.decode(input_1.get(i).toString()) instanceof Number &&
                        ud.decode(input_2.get(j).toString()) instanceof Number) {
                    if ((ValueDistances.NUMBER_DISTANCE.distance(input_1.get(i).toString(), input_2.get(j).toString())) <= threshold_final) {
                        List<Element> matchingList = new ArrayList<>();
                        matchingList.add(new Element("Number", input_1.get(i).toString()));
                        matchingList.add(new Element("Number", input_2.get(j).toString()));
                        mapResult.put(t, matchingList);
                        t = t + 1;
                    }
                }

            }
        }

        model.addAttribute("mapResult", mapResult);
        return "get-final-map";

    }
}
