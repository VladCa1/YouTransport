package com.trans.services;

import java.util.*;

import com.trans.models.agent.CAgent;
import com.trans.models.agent.FTPAgent;
import com.trans.models.characteristic.GoodsEntityProperty;
import com.trans.models.offer.Offer;
import com.trans.models.offer.TransportOffer;
import com.trans.models.transport.*;
import com.trans.repositories.*;
import com.trans.serviceInterface.models.DriverResultEntryDTO;
import com.trans.serviceInterface.models.TransportFormResultDTO;
import com.trans.utils.BasicUtils;
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
import com.trans.serviceInterface.models.GoodsFormResultDTO;

@Service
public class OfferServiceImpl implements OfferService{

	public static final String FREE_FLOW = "FreeFlow";
	public static final String PALLET = "Pallet";
	public static final String LIQUID = "Liquid";
	public static final String DAY = "Day";
	public static final String DISTANCE = "Distance";
	public static final String HOUR = "Hour";
	@Autowired
	private OfferRepository offerRepository;
	
	@Autowired
	private RouteRepository routeRepository;
	
	@Autowired
	private LocationRepository locationRepository;
	
	@Autowired
	private GoodsEntityRepository goodsRepository;

	@Autowired
	private GoodsPropertyRepository goodsPropertyRepository;

	@Autowired
	private DriverRepository driverRepository;
	
	@Autowired
	private AgentService agentService;

	@Autowired
	private DriverService driverService;

	@Override
	public boolean saveUpdateGoods(GoodsFormResultDTO offer, String token) {
		
		if(offer.getId() == null) {
			return save(offer, token);
		}else {
			return update(offer, token);
		}	
				
	}		
	private boolean save(GoodsFormResultDTO offer, String token) {
		GoodsOffer goodsOffer = new GoodsOffer();

		buildGoodsOffer(offer, goodsOffer, token);
		try {
			offerRepository.save(goodsOffer);
			
			return true;
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	private void buildGoodsOffer(GoodsFormResultDTO offer, GoodsOffer goodsOffer, String token) {
		Location from = locationRepository.findByCountryAndCity(offer.getFromLocationCountry(), offer.getFromLocationCity());
		Location to = locationRepository.findByCountryAndCity(offer.getToLocationCountry(), offer.getToLocationCity());
		Route route = new Route(to, from, offer.getDistance(), offer.getDuration());
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
		List<GoodsEntityProperty> goodsEntityProperties = new ArrayList<>();
		goodsEntityProperties.add(new GoodsEntityProperty(offer.getCharacteristicOne(), 1L));
		if(goodsOffer.getGoods() != null && !goodsOffer.getGoods().isEmpty()){
			goodsOffer.getGoods().clear();
		}
		if(offer.getCharacteristicTwo() != null && !offer.getCharacteristicTwo().isEmpty()){
			goodsEntityProperties.add(new GoodsEntityProperty(offer.getCharacteristicTwo(), 2L));
		}
		goods.setProperties(goodsEntityProperties);
		List<GoodsEntity> goodsList = new ArrayList<>();
		goodsList.add(goods);
		if(goodsOffer.getGoods() != null){
			goodsOffer.getGoods().addAll(goodsList);
		}else{
			goodsOffer.setGoods(goodsList);
		}
		goodsOffer.setFromDate(offer.getFromDate());
		goodsOffer.setToDate(offer.getToDate());
		goodsOffer.setProviderAgent((CAgent) agentService.findByToken(token));
		Pay pay = null;
		switch(offer.getPayType()) {
		case(Pay.PER_DAY):
			pay = new DayPay(offer.getPayValue(), offer.getPayCurrency());
			break;
		case(Pay.PER_HOUR):
			pay = new HourPay(offer.getPayValue(), offer.getPayCurrency());
			break;
		case(Pay.PER_KM):
			pay = new DistancePay(offer.getPayValue(), offer.getPayCurrency());
			break;
		}
		goodsOffer.setPay(pay);
		goodsOffer.setProviderData(offer.getDescription());
		goodsOffer.setDescription(offer.getDescription());
		goodsOffer.setRoute(route);
	}


	private boolean update(GoodsFormResultDTO offer, String token) {
		GoodsOffer goodsOffer = offerRepository.getGoodsById(Long.valueOf(offer.getId()));

		buildGoodsOffer(offer, goodsOffer, token);
		try {
			offerRepository.save(goodsOffer);
			return true;
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}


	@Override
	public List<GoodsFormResultDTO> getAllGoodsForUser(String token) {
		return mapToFormResult(offerRepository.findAllGoodsByToken(token));
	}
	
	private List<GoodsFormResultDTO> mapToFormResult(List<GoodsOffer> offers) {
		List<GoodsFormResultDTO> result = new ArrayList<>();
		for (GoodsOffer goodsOffer : offers) {
			GoodsFormResultDTO item = new GoodsFormResultDTO();
			item.setId(goodsOffer.getId().toString());
			doGoodsMapping(goodsOffer, item);
			item.setDescription(goodsOffer.getDescription());
			for (GoodsEntityProperty property:
			goodsOffer.getGoods().get(0).getProperties()) {
				if(property.getNumberOfChar().equals(1L)){
					item.setCharacteristicOne(property.getDetails());
				}else if(property.getNumberOfChar().equals(2L)){
					item.setCharacteristicTwo(property.getDetails());
				}
			}
			item.setFromDate(goodsOffer.getFromDate());
			item.setToDate(goodsOffer.getToDate());
			item.setFromLocationCity(goodsOffer.getRoute().getFrom().getCity());
			item.setFromLocationCountry(goodsOffer.getRoute().getFrom().getCountry());
			item.setToLocationCity(goodsOffer.getRoute().getTo().getCity());
			item.setToLocationCountry(goodsOffer.getRoute().getTo().getCountry());
			item.setDistance(goodsOffer.getRoute().getDistance());
			item.setDuration(goodsOffer.getRoute().getDuration());
			doPayMapping(goodsOffer, item);
			result.add(item);

		}
		return result;
	}
	
	private void doGoodsMapping(GoodsOffer goodsOffer, GoodsFormResultDTO item) {
		switch (goodsOffer.getGoods().get(0).getDiscriminatorValue()) {
		case FREE_FLOW:
			item.setGoodsType(GoodsEntity.FREE_FLOW_GOODS);
			item.setGoodsSize(((FreeFlowGoods)goodsOffer.getGoods().get(0)).getValue());
			break;
		case PALLET:
			item.setGoodsType(GoodsEntity.PALLET_GOODS);
			item.setGoodsSize(((PalletGoods)goodsOffer.getGoods().get(0)).getValue());
			item.setGoodsPalletSize(((PalletGoods)goodsOffer.getGoods().get(0)).getTonnage());
			break;
		case LIQUID:
			item.setGoodsType(GoodsEntity.LIQUID_GOODS);
			item.setGoodsSize(((LiquidGoods)goodsOffer.getGoods().get(0)).getValue());
			break;
		default:
			break;
		}
	}
	
	private void doPayMapping(Offer Offer, GoodsFormResultDTO item) {
		switch (Offer.getPay().getDiscriminatorValue()) {
		case DAY:
			item.setPayType(Pay.PER_DAY);
			item.setPayValue(((DayPay)Offer.getPay()).getRate());
			item.setPayCurrency(((DayPay) Offer.getPay()).getCurrency());
			break;
		case DISTANCE:
			item.setPayType(Pay.PER_KM);
			item.setPayValue(((DistancePay)Offer.getPay()).getRate());
			item.setPayCurrency(((DistancePay) Offer.getPay()).getCurrency());
			break;
		case HOUR:
			item.setPayType(Pay.PER_HOUR);
			item.setPayValue(((HourPay)Offer.getPay()).getRate());
			item.setPayCurrency(((HourPay) Offer.getPay()).getCurrency());
			break;
		default:
			break;
		}
	}

	private void doPayMapping(Offer Offer, TransportFormResultDTO item) {
		switch (Offer.getPay().getDiscriminatorValue()) {
			case DAY:
				item.setPayType(Pay.PER_DAY);
				item.setPayValue(((DayPay)Offer.getPay()).getRate());
				item.setPayCurrency(((DayPay) Offer.getPay()).getCurrency());
				break;
			case DISTANCE:
				item.setPayType(Pay.PER_KM);
				item.setPayValue(((DistancePay)Offer.getPay()).getRate());
				item.setPayCurrency(((DistancePay) Offer.getPay()).getCurrency());
				break;
			case HOUR:
				item.setPayType(Pay.PER_HOUR);
				item.setPayValue(((HourPay)Offer.getPay()).getRate());
				item.setPayCurrency(((HourPay) Offer.getPay()).getCurrency());
				break;
			default:
				break;
		}
	}
	
	@Override
	public boolean deleteGoodsOffer(String token, Long id) {
		String offerToken = offerRepository.getTokenForIdGoods(id);
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
		return mapToFormResult(offerRepository.findAllGoods());
	}

	@Override
	public boolean checkUserOwnsOffer(String token, Long offerId, String type) {
		if(type.equals("goods")){
			if(offerRepository.getTokenForIdGoods(offerId).equals(token)){
				return true;
			}else{
				return false;
			}
		}else if(type.equals("transport")){
			if(offerRepository.getTokenForIdTransport(offerId).equals(token)){
				return true;
			}else{
				return false;
			}
		}else {
			return false;
		}
	}

	@Override
	public GoodsFormResultDTO getGoodsOffer( Long id) {
		return mapToFormResult(Arrays.asList(offerRepository.getGoodsById(id))).get(0);
	}

	@Override
	public String getGoodsTokenForId(Long id) {
		return offerRepository.getTokenForIdGoods(id);
	}


	@Override
	public boolean saveUpdateTransport(TransportFormResultDTO offer, String token){
		if(offer.getId() == null) {
			return saveTransport(offer, token);
		}else {
			return updateTransport(offer, token);
		}
	}

	@Override
	public boolean deleteTransportOffer(String token, Long id) {
		String offerToken = offerRepository.getTokenForIdTransport(id);
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
	public List<TransportFormResultDTO> getAllTransportForUser(String token) {
		return mapToTransportFormResultWithCriteria(offerRepository.findAllTransport(), token);
	}

	private List<TransportFormResultDTO> mapToTransportFormResultWithCriteria(List<TransportOffer> allTransport, String token) {
		List<TransportFormResultDTO> result = new ArrayList<>();
		for (TransportOffer offer:
				allTransport) {
			TransportFormResultDTO dto = new TransportFormResultDTO();
			dto.setId(offer.getId());
			dto.setDescription(offer.getDescription());
			dto.setDistance(offer.getRoute().getDistance());
			dto.setDuration(offer.getRoute().getDuration());
			Long driverid = offer.getTransportEntity().getDriverId();
			Driver driver = driverRepository.getById(driverid);
			dto.setDriver(DriverServiceImpl.mapToDTO(new ArrayList<Driver>(){{
				add(driver);
			}}).get(0));
			dto.setFabricationYear(BasicUtils.convertToLocalDateViaSqlDate(offer.getTransportEntity().getFabricationDate()));
			dto.setFromDate(offer.getFromDate());
			dto.setHorsePower(offer.getTransportEntity().getHorsePower());
			dto.setMake(offer.getTransportEntity().getMake());
			dto.setVehicleType(offer.getTransportEntity().getDiscriminatorValue());
			dto.setType(offer.getTransportEntity().getType());
			dto.setTonnage(offer.getTransportEntity().getTonnage());
			dto.setHazardousMaterialsAccepted(offer.getTransportEntity().isHazardousMaterialsAccepted());
			dto.setMaxAcceptedDistanceFromSource(offer.getMaxDistanceFromSource());
			doPayMapping(offer, dto);
			Set<String> Set = new HashSet<String>(offer.getTransportEntity().getAcceptedGoods());
			dto.setGoodsTypes(Set);
			dto.setFromLocationCity(offer.getRoute().getFrom().getCity());
			dto.setFromLocationCountry(offer.getRoute().getFrom().getCountry());
			if(offer.getRoute().getTo() != null){
				dto.setToLocationCity(offer.getRoute().getTo().getCity());
				dto.setToLocationCountry(offer.getRoute().getTo().getCountry());
			}
			if(offer.getProviderAgent().getToken().equals(token)){
				result.add(dto);
			}
		}
		return result;
	}

	@Override
	public List<TransportFormResultDTO> getAllTransport() {
		return mapToTransportFormResult(offerRepository.findAllTransport());
	}

	@Override
	public TransportFormResultDTO getTransportOffer(Long id) {
		return mapToTransportFormResult(Arrays.asList(new TransportOffer[]{offerRepository.getTransportById(id)})).get(0);
	}

	@Override
	public String getTransportTokenForId(Long id) {
		return offerRepository.getTokenForIdTransport(id);
	}

	private List<TransportFormResultDTO> mapToTransportFormResult(List<TransportOffer> allTransport) {
		List<TransportFormResultDTO> result = new ArrayList<>();
		for (TransportOffer offer:
			 allTransport) {
			TransportFormResultDTO dto = new TransportFormResultDTO();
			dto.setId(offer.getId());
			dto.setDescription(offer.getDescription());
			dto.setDistance(offer.getRoute().getDistance());
			dto.setDuration(offer.getRoute().getDuration());
			dto.setDriver(DriverServiceImpl.mapToDTO(new ArrayList<Driver>(){{
				add(driverRepository.getById(offer.getTransportEntity().getDriverId()));
			}}).get(0));
			dto.setFabricationYear(BasicUtils.convertToLocalDateViaSqlDate(offer.getTransportEntity().getFabricationDate()));
			dto.setFromDate(offer.getFromDate());
			dto.setHorsePower(offer.getTransportEntity().getHorsePower());
			dto.setMake(offer.getTransportEntity().getMake());
			dto.setVehicleType(offer.getTransportEntity().getDiscriminatorValue());
			dto.setType(offer.getTransportEntity().getType());
			dto.setTonnage(offer.getTransportEntity().getTonnage());
			dto.setHazardousMaterialsAccepted(offer.getTransportEntity().isHazardousMaterialsAccepted());
			dto.setMaxAcceptedDistanceFromSource(offer.getMaxDistanceFromSource());
			doPayMapping(offer, dto);
			Set<String> Set = new HashSet<String>(offer.getTransportEntity().getAcceptedGoods());
			dto.setGoodsTypes(Set);
			dto.setFromLocationCity(offer.getRoute().getFrom().getCity());
			dto.setFromLocationCountry(offer.getRoute().getFrom().getCountry());
			if(offer.getRoute().getTo() != null){
				dto.setToLocationCity(offer.getRoute().getTo().getCity());
				dto.setToLocationCountry(offer.getRoute().getTo().getCountry());
			}
			result.add(dto);
		}
		return result;
	}

	private boolean updateTransport(TransportFormResultDTO offer, String token) {
		TransportOffer transportOffer = offerRepository.getTransportById(offer.getId());

		buildTransportOffer(transportOffer, offer, token);

		try{
			offerRepository.save(transportOffer);
			return true;
		}catch (Exception e){
			e.printStackTrace();
			return false;
		}
	}

	private void buildTransportOffer(TransportOffer transportOffer, TransportFormResultDTO offer, String token) {

		TransportEntity transportEntity = null;

		if(offer.getVehicleType().equals("Truck")){
			transportEntity = new Truck();
		}else if(offer.getVehicleType().equals("BigVehicle")){
			transportEntity = new BigVehicle();
		}else if(offer.getVehicleType().equals("SmallVehicle")){
			transportEntity = new SmallVehicle();
		}
		Driver driver = driverService.getDriverForUser(token, offer.getDriver().getId());
		driver.setAssigned(true);
		transportEntity.setDriverId(driver.getId());
		transportEntity.setFabricationDate(BasicUtils.convertToDateViaSqlDate(offer.getFabricationYear()));
		transportEntity.setHazardousMaterialsAccepted(offer.getHazardousMaterialsAccepted());
		transportEntity.setMake(offer.getMake());
		transportEntity.setType(offer.getType());
		transportEntity.setTonnage(offer.getTonnage());
		transportEntity.setHorsePower(offer.getHorsePower());
		transportEntity.setAcceptedGoods(new ArrayList<String>(offer.getGoodsTypes()));

		transportOffer.setFromDate(offer.getFromDate());
		transportOffer.setTransportEntity(transportEntity);
		Pay pay = null;
		switch(offer.getPayType()) {
			case(Pay.PER_DAY):
				pay = new DayPay(offer.getPayValue(), offer.getPayCurrency());
				break;
			case(Pay.PER_HOUR):
				pay = new HourPay(offer.getPayValue(), offer.getPayCurrency());
				break;
			case(Pay.PER_KM):
				pay = new DistancePay(offer.getPayValue(), offer.getPayCurrency());
				break;
		}
		transportOffer.setPay(pay);
		if(offer.getMaxAcceptedDistanceFromSource() == null){
			Location from = locationRepository.findByCountryAndCity(offer.getFromLocationCountry(), offer.getFromLocationCity());
			Location to = locationRepository.findByCountryAndCity(offer.getToLocationCountry(), offer.getToLocationCity());
			transportOffer.setRoute(new Route(to, from, offer.getDistance(), offer.getDuration()));
		}else{
			Location from = locationRepository.findByCountryAndCity(offer.getFromLocationCountry(), offer.getFromLocationCity());
			transportOffer.setRoute(new Route(null, from, null, null));
			transportOffer.setMaxDistanceFromSource(offer.getMaxAcceptedDistanceFromSource());
		}

		transportOffer.setDescription(offer.getDescription());
		transportOffer.setProviderAgent((FTPAgent) agentService.findByToken(token));
		transportOffer.setProviderData(offer.getDescription());

	}

	private boolean saveTransport(TransportFormResultDTO offer, String token) {
		TransportOffer transportOffer = new TransportOffer();

		buildTransportOffer(transportOffer, offer, token);

		try{
			offerRepository.save(transportOffer);
			return true;
		}catch (Exception e){
			e.printStackTrace();
			return false;
		}
	}

}
