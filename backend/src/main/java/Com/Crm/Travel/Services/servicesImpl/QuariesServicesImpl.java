package Com.Crm.Travel.Services.servicesImpl;

import Com.Crm.Travel.Entities.AppUser;
import Com.Crm.Travel.Entities.DTO.QuariesDetailDTO;
import Com.Crm.Travel.Entities.DTO.QueriesCommentDTO;
import Com.Crm.Travel.Entities.EntitesHelper.QuariesHelper;
import Com.Crm.Travel.Entities.Quaries;
import Com.Crm.Travel.Entities.QueriesComment;
import Com.Crm.Travel.Repo.QuariesRepo;
import Com.Crm.Travel.Services.AppUserServices;
import Com.Crm.Travel.Services.QuariesServices;
import Com.Crm.Travel.Services.QueriesCommentService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service

public class QuariesServicesImpl implements QuariesServices {

	private final QuariesRepo quariesRepo;
	private final AppUserServices appUserServices;

	private final QueriesCommentService queriesCommentService;

	public QuariesServicesImpl(QuariesRepo quariesRepo, AppUserServices appUserServices,
							   QueriesCommentService queriesCommentService) {
		this.quariesRepo = quariesRepo;
		this.appUserServices = appUserServices;
		this.queriesCommentService = queriesCommentService;
	}

	@SuppressWarnings("null")
	@Override
	@Transactional
	public boolean saveQuaries(QuariesHelper helper, Authentication principal) {
		try {
			AppUser assignedUser = Optional.ofNullable(helper.assignedUseremail()).filter(StringUtils::hasText)
					.map(appUserServices::findUserByEmail).orElseGet(() -> (AppUser) principal.getPrincipal());
			Quaries quaries = Quaries.builder().requesterName(helper.requesterName()).contactNo(helper.contactNo())
					.email(helper.email()).Destination(helper.Destination()).fromLocation(helper.fromLocation())
					.fromDate(helper.fromDate()).toDate(helper.toDate()).quotedPrice(helper.quotedPrice())
					.requiredServices(helper.requiredServices()).quariesStatus(helper.quariesStatus())
					.quariesPriority(helper.quariesPriority()).appUser(assignedUser).build();
			quariesRepo.save(quaries);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public Page<Quaries> findByAppUser_id(Long Id, int page, int size, String sortBy) {
		Pageable pageable1 = PageRequest.of(page, size, Sort.by(sortBy).descending());
		return quariesRepo.findByAppUser_id(Id, pageable1);
	}

	@Override
	public Page<Quaries> getQueriesByUserWithFilters(Long userId, String status, int page, int size, String sortBy) {
		Pageable pageable1 = PageRequest.of(page, size, Sort.by(sortBy).descending());
		return quariesRepo.findByAppUser_idAndQuariesStatus(userId, status, pageable1);

	}

	@Override
	public Page<Quaries> findByAppUser_idAndQuariesPriority(Long userId, String priority, int page, int size,
															String sortBy) {
		Pageable pageable1 = PageRequest.of(page, size, Sort.by(sortBy).descending());
		return quariesRepo.findByAppUser_AndQuariesPriority(userId, priority, pageable1);

	}

	@Transactional
	@Override
	public Page<QuariesDetailDTO> findAllWithFilters(QuariesHelper helper, AppUser user, int page, int size,
													 String sortBy) {

		Page<Quaries> queries;
		Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());

		ExampleMatcher matcher = ExampleMatcher.matchingAll().withIgnoreNullValues().withIgnoreCase()
				.withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING).withIgnoreCase();
		Quaries.QuariesBuilder builder = Quaries.builder().Destination(helper.Destination())
				.quariesStatus(helper.quariesStatus()).quariesPriority(helper.quariesPriority())
				.requesterName(helper.requesterName());

		if (!user.isSuperAdmin()) {
			builder.appUser(user);
		}
		Quaries filterCriteria = builder.build();
		Example<Quaries> example = Example.of(filterCriteria, matcher);

		queries = quariesRepo.findAll(example, pageable);
		List<Long> queriesId = queries.stream().map(Quaries::getSerialNumber).toList();
		List<QueriesComment> commentById = queriesCommentService.findByQuarie_SerialNumberIn(queriesId);
		Map<Long, List<QueriesComment>> commentsByQueryId = commentById.stream()
				.collect(Collectors.groupingBy(c -> c.getQuarie().getSerialNumber()));

		return queries.map(query -> {
			List<QueriesComment> comments = commentsByQueryId.getOrDefault(query.getSerialNumber(), List.of());
			List<QueriesCommentDTO> commentDTOS = comments.stream().map(c -> new QueriesCommentDTO(c.getId(),
					c.getCommentText(), c.getCreatedAt(), c.getModifiedAt(), c.getAppUser().toString())).toList();

			return QuariesDetailDTO.builder().requesterName(query.getRequesterName())
					.destination(query.getDestination()).fromLocation(query.getFromLocation())
					.fromDate(query.getFromDate()).toDate(query.getToDate()).quotedPrice(query.getQuotedPrice())
					.requiredServices(query.getRequiredServices()).createdAt(query.getCreatedAt())
					.quariesStatus(query.getQuariesStatus()).quariesPriority(query.getQuariesPriority())
					.serialNumber(query.getSerialNumber()).contactNo(query.getContactNo()).email(query.getEmail())
					.quariesCommentDTOS(commentDTOS).build();
		});

	}

	@Override
	public Quaries findById(Long Id) {
		return quariesRepo.findById(Id).orElseThrow(() -> new EntityNotFoundException("Querie By the Id Not Found"));
	}

}
