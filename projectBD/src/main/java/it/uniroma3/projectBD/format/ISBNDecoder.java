package it.uniroma3.projectBD.format;


import it.uniroma3.projectBD.value.ISBN;

import static it.uniroma3.projectBD.format.Regexps.ISBN_REGEXP;

public class ISBNDecoder implements TypeDecoder {

	static final private FormatRule ISBN_RULE = new FormatRule(ISBN_REGEXP);
	
	@Override
	public ISBN decode(String string) {
		final String minusFree = string.replaceAll("-", "");
		if (minusFree.length()==10 || minusFree.length()==13 || minusFree.contains("/")) {
			return new ISBN(ISBN_RULE.extract(minusFree));
			//return ISBN_RULE.extract(minusFree);
		}
		return null;
	}

}



