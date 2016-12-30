package rebu.web.rest;

import com.codahale.metrics.annotation.Timed;
import rebu.domain.Result;
import rebu.service.ResultService;
import rebu.web.rest.util.HeaderUtil;
import rebu.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Result.
 */
@RestController
@RequestMapping("/api")
public class ResultResource {

    private final Logger log = LoggerFactory.getLogger(ResultResource.class);
        
    @Inject
    private ResultService resultService;

    /**
     * POST  /results : Create a new result.
     *
     * @param result the result to create
     * @return the ResponseEntity with status 201 (Created) and with body the new result, or with status 400 (Bad Request) if the result has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/results")
    @Timed
    public ResponseEntity<Result> createResult(@RequestBody Result result) throws URISyntaxException {
        log.debug("REST request to save Result : {}", result);
        if (result.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("result", "idexists", "A new result cannot already have an ID")).body(null);
        }
        Result newResult = resultService.save(result);
        return ResponseEntity.created(new URI("/api/results/" + newResult.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("result", newResult.getId().toString()))
            .body(newResult);
    }

    /**
     * PUT  /results : Updates an existing result.
     *
     * @param result the result to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated result,
     * or with status 400 (Bad Request) if the result is not valid,
     * or with status 500 (Internal Server Error) if the result couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/results")
    @Timed
    public ResponseEntity<Result> updateResult(@RequestBody Result result) throws URISyntaxException {
        log.debug("REST request to update Result : {}", result);
        if (result.getId() == null) {
            return createResult(result);
        }
        Result newResult = resultService.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("result", newResult.getId().toString()))
            .body(newResult);
    }

    /**
     * GET  /results : get all the results.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of results in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/results")
    @Timed
    public ResponseEntity<List<Result>> getAllResults(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Results");
        Page<Result> page = resultService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/results");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /results/:id : get the "id" result.
     *
     * @param id the id of the result to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the result, or with status 404 (Not Found)
     */
    @GetMapping("/results/{id}")
    @Timed
    public ResponseEntity<Result> getResult(@PathVariable Long id) {
        log.debug("REST request to get Result : {}", id);
        Result newResult = resultService.findOne(id);
        return Optional.ofNullable(newResult)
            .map(result -> new ResponseEntity<>(
            	newResult,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /results/:id : delete the "id" result.
     *
     * @param id the id of the result to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/results/{id}")
    @Timed
    public ResponseEntity<Void> deleteResult(@PathVariable Long id) {
        log.debug("REST request to delete Result : {}", id);
        resultService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("result", id.toString())).build();
    }

}
