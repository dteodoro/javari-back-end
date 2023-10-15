package com.dteodoro.javari.game.controller;

import com.dteodoro.javari.commons.dto.BettorDTO;
import com.dteodoro.javari.commons.dto.TeamDTO;
import com.dteodoro.javari.game.config.FileStorageProperties;
import com.dteodoro.javari.game.service.BettorService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MimeType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/game/bettor")
@RequiredArgsConstructor
@Slf4j
public class BettorController {

	private final BettorService bettorService;
	private final FileStorageProperties fileStorageProperties;

	@GetMapping("/{id}")
	public ResponseEntity<BettorDTO> getBettorDetail(@PathVariable("id") UUID bettorId) {
		// TODO check if bettor id is the same bettor current login
		if (bettorId != null) {
			return ResponseEntity.ok(bettorService.findBettorDetails(bettorId));
		}
		return ResponseEntity.notFound().build();
	}

	@GetMapping("/{id}/rivals")
	public ResponseEntity<List<BettorDTO>> getRivals(@PathVariable("id") UUID bettorId) {
		if (bettorId != null) {
			List<BettorDTO> rivals = bettorService.findRivals(bettorId);
			return ResponseEntity.ok(rivals);
		}
		return ResponseEntity.notFound().build();
	}

	@PostMapping("/{id}/favoriteTeam/{teamId}")
	public TeamDTO setFavoriteTeam(@PathVariable("id") UUID bettorId, @PathVariable("teamId") UUID teamId) {
		return bettorService.setFavoriteTeam(bettorId, teamId);
	}

	@GetMapping("/{id}/favoriteTeam")
	public TeamDTO getFavoriteTeam(@PathVariable("id") UUID bettorId) {
		return bettorService.findFavoriteTeam(bettorId);
	}

	@DeleteMapping("/{id}/favoriteTeam/{teamId}")
	public void removeFavoriteTeam(@PathVariable("id") UUID bettorId, @PathVariable("teamId") UUID teamId) {
		bettorService.setFavoriteTeam(bettorId, null);
	}

	@GetMapping("/{id}/{roleName}")
	public boolean hasPermission(@PathVariable(name = "id", required = true) UUID bettorId,
			@PathVariable(name = "roleName", required = true) String roleName) {
		return bettorService.hasPermission(bettorId, roleName);
	}

	@PostMapping("/{id}/uploadImage")
	public ResponseEntity<String> uploadImageFile(@PathVariable("id") UUID bettorId, @RequestBody MultipartFile file) {
		String fileNameToStorage = StringUtils
				.cleanPath(bettorId + "." + MimeType.valueOf(file.getContentType()).getSubtype());
		Path uploadDir = Paths.get(fileStorageProperties.getUploadDir());
		Path targetLocation = uploadDir.resolve(fileNameToStorage);
		try {
			file.transferTo(targetLocation);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		if (StringUtils.hasText(fileNameToStorage)) {
			bettorService.updateImage(fileNameToStorage, bettorId);
			String uriFileDownload = ServletUriComponentsBuilder.fromCurrentContextPath()
					.path("/api/v1/game/bettor/image/")
					.path(fileNameToStorage)
					.toUriString();
			return ResponseEntity.created(URI.create(uriFileDownload)).build();
		}
		return ResponseEntity.badRequest().build();
	}

	@GetMapping("/image/{fileName:.+}")
	public ResponseEntity<Resource> getBettorImage(@PathVariable String fileName, HttpServletRequest request) {
		Path filePath = Paths.get(fileStorageProperties.getUploadDir() + fileName).normalize();

		try {
			Resource resource = new UrlResource(filePath.toUri());
			String contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());

			if (contentType == null) {
				contentType = "application/octet-stream";
			}
			return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
					.body(resource);
		} catch (Exception e) {
			return ResponseEntity.badRequest().build();
		}
	}

}
