package com.trans.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trans.models.goods.FreeFlowGoods;
import com.trans.models.goods.GoodsEntity;
import com.trans.models.goods.LiquidGoods;
import com.trans.models.goods.PalletGoods;
import com.trans.models.offer.GoodsOffer;
import com.trans.models.pay.DayPay;
import com.trans.models.pay.DistancePay;
import com.trans.models.pay.HourPay;
import com.trans.models.pay.Pay;
import com.trans.models.route.Location;
import com.trans.models.route.Route;
import com.trans.repositories.GoodsEntityRepository;
import com.trans.repositories.LocationRepository;
import com.trans.repositories.OfferRepository;
import com.trans.repositories.RouteRepository;
import com.trans.serviceInterface.models.GoodsFormResultDTO;

@Service
public class OfferServiceImpl implements OfferService{

	@Autowired
	private OfferRepository offerRepository;
	
	@Autowired
	private RouteRepository routeRepository;
	
	@Autowired
	private LocationRepository locationRepository;
	
	@Autowired
	private GoodsEntityRepository goodsRepository;
	
	@Autowired
	private AgentService agentService;

	@Override
	public boolean saveUpdate(GoodsFormResultDTO offer, String token) {
		
		if(offer.getId() == null) {
			return save(offer, token);
		}else {
			return update(offer, token);
		}	
				
	}		
	private boolean save(GoodsFormResultDTO offer, String token) {
		GoodsOffer goodsOffer = new GoodsOffer();
		
		Location from = locationRepository.findByCountryAndCity(offer.getFromLocationCountry(), offer.getFromLocationCity());
		Location to = locationRepository.findByCountryAndCity(offer.getToLocationCountry(), offer.getToLocationCity());
		List<Location> intermediary = new ArrayList<>();
		intermediary.add(from);
		Route route = new Route(to, from, intermediary);
//		routeRepository.save(route);
		GoodsEntity goods = null;
		switch(offer.getGoodsType()) {
		case(GoodsEntity.FREE_FLOW_GOODS):
			goods = new FreeFlowGoods(offer.getGoodsSize(), "L", 5000.0);
			break;
		case(GoodsEntity.LIQUID_GOODS):
			goods = new LiquidGoods(offer.getGoodsSize(), "L", 5000.0);
			break;
		case(GoodsEntity.PALLET_GOODS):
			goods = new PalletGoods(offer.getGoodsSize(), "Number x T", offer.getGoodsPalletSize());
			break;
		}
		List<GoodsEntity> goodsList = new ArrayList<>();
		goodsList.add(goods);
		goodsOffer.setGoods(goodsList);
		goodsOffer.setFromDate(offer.getFromDate());
		goodsOffer.setToDate(offer.getToDate());
		goodsOffer.setProviderAgent(agentService.findByToken(token));
		Pay pay = null;
		switch(offer.getPayType()) {
		case(Pay.PER_DAY):
			pay = new DayPay(offer.getPayValue());
			break;
		case(Pay.PER_HOUR):
			pay = new HourPay(offer.getPayValue());
			break;
		case(Pay.PER_KM):
			pay = new DistancePay(offer.getPayValue());
			break;
		}
		goodsOffer.setPay(pay);
		goodsOffer.setProviderData(offer.getDescription());
		goodsOffer.setDescription(offer.getDescription());
		goodsOffer.setRoute(route);
		try {
			offerRepository.save(goodsOffer);
			
			return true;
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}
	

	private boolean update(GoodsFormResultDTO offer, String token) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public List<GoodsFormResultDTO> getAllGoodsForUser(String token) {
		return mappToFormResult(offerRepository.findAllGoodsByToken(token));
	}
	
	private List<GoodsFormResultDTO> mappToFormResult(List<GoodsOffer> offers) {
		List<GoodsFormResultDTO> result = new ArrayList<>();
		for (GoodsOffer goodsOffer : offers) {
			GoodsFormResultDTO item = new GoodsFormResultDTO();
			item.setId(goodsOffer.getId().toString());
			doGoodsMapping(goodsOffer, item);
			item.setDescription(goodsOffer.getDescription());
			item.setCharacteristicOne("NOT AVAILABLE");
			item.setCharacteristicTwo("NOT AVAILABLE");
			item.setFromDate(goodsOffer.getFromDate());
			item.setToDate(goodsOffer.getToDate());
			item.setFromLocationCity(goodsOffer.getRoute().getFrom().getCity());
			item.setFromLocationCountry(goodsOffer.getRoute().getFrom().getCountry());
			item.setToLocationCity(goodsOffer.getRoute().getTo().getCity());
			item.setToLocationCountry(goodsOffer.getRoute().getTo().getCountry());
			doPayMapping(goodsOffer, item);
			result.add(item);

		}
		return result;
	}
	
	private void doGoodsMapping(GoodsOffer goodsOffer, GoodsFormResultDTO item) {
		switch (goodsOffer.getGoods().get(0).getDiscriminatorValue()) {
		case "FreeFlow":
			item.setGoodsType(GoodsEntity.FREE_FLOW_GOODS);
			item.setGoodsSize(((FreeFlowGoods)goodsOffer.getGoods().get(0)).getValue());
			break;
		case "Pallet":
			item.setGoodsType(GoodsEntity.PALLET_GOODS);
			item.setGoodsSize(((PalletGoods)goodsOffer.getGoods().get(0)).getValue());
			item.setGoodsPalletSize(((PalletGoods)goodsOffer.getGoods().get(0)).getTonnage());
			break;
		case "Liquid":
			item.setGoodsType(GoodsEntity.LIQUID_GOODS);
			item.setGoodsSize(((LiquidGoods)goodsOffer.getGoods().get(0)).getValue());
			break;
		default:
			break;
		}
	}
	
	private void doPayMapping(GoodsOffer goodsOffer, GoodsFormResultDTO item) {
		switch (goodsOffer.getPay().getDiscriminatorValue()) {
		case "Day":
			item.setPayType(Pay.PER_DAY);
			item.setPayValue(((DayPay)goodsOffer.getPay()).getRate());
			break;
		case "Distance":
			item.setPayType(Pay.PER_KM);
			item.setPayValue(((DistancePay)goodsOffer.getPay()).getRate());
			break;
		case "Hour":
			item.setPayType(Pay.PER_HOUR);
			item.setPayValue(((HourPay)goodsOffer.getPay()).getRate());
			break;
		default:
			break;
		}
	}
	
	@Override
	public boolean deleteGoodsOffer(String token, Long id) {
		String offerToken = offerRepository.getTokenForId(id);
		if(!token.equalsIgnoreCase(offerToken)) {
			return false;
		}
		try {
			offerRepository.deleteById(id);
			return true;
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	@Override
	public List<GoodsFormResultDTO> getAllGoods() {
		return mappToFormResult(offerRepository.findAllGoods());
	}
	
}
