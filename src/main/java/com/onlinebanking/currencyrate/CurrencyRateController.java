package com.onlinebanking.currencyrate;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.onlinebanking.common.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
		Map<String, Object> data = new HashMap<>();
		JsonObject o = parser.parse(jsonInput).getAsJsonObject();
		String currencyCode = o.get("currencyCode").getAsString();
		Double buyRate = o.get("buyRate").getAsDouble();
		Double sellRate = o.get("sellRate").getAsDouble();

		CurrencyRate newCurrencyRate = new CurrencyRate();
		newCurrencyRate.setCurrencyCode(currencyCode);
		newCurrencyRate.setBuyRate(buyRate);
		newCurrencyRate.setSaleRate(sellRate);
		newCurrencyRate.setDate(new Date());

		newCurrencyRate = currencyRateRepository.save(newCurrencyRate);

		if(newCurrencyRate.getId() != null){
			data.put("message","");
			return new Response(511,"Successful", data);
		}

		data.put("message", "Try again later");
		return new Response(512, "Failed", data);

	}
}
