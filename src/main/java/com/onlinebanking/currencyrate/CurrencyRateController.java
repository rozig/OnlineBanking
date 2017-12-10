package com.onlinebanking.currencyrate;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.onlinebanking.common.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/currency")
public class CurrencyRateController {

	@Autowired
	CurrencyRateRepository currencyRateRepository;

	@PostMapping("/fetch_latest")
	public @ResponseBody List<CurrencyRate> fetchLatest(){
		return currencyRateRepository.findLatest();
	}

	@PostMapping("/update_currency_rate")
	public @ResponseBody
	Response updateCurrencyRate(@RequestBody String jsonInput){
		JsonParser parser = new JsonParser();
		JsonObject o = parser.parse(jsonInput).getAsJsonObject();
		CurrencyType currencyCode = CurrencyType.valueOf(o.get("currencyCode").getAsString());
		Double buyRate = o.get("buyRate").getAsDouble();
		Double sellRate = o.get("sellRate").getAsDouble();

		CurrencyRate newCurrencyRate = new CurrencyRate();
		newCurrencyRate.setCurrencyCode(currencyCode);
		newCurrencyRate.setBuyRate(buyRate);
		newCurrencyRate.setSaleRate(sellRate);
		newCurrencyRate.setDate(new Date());

		newCurrencyRate = currencyRateRepository.save(newCurrencyRate);

		if(newCurrencyRate.getId() != null){
			return new Response(511,"Successful", "");
		}

		return new Response(512, "Failed", "Try again later");

	}
}
