package br.com.temwifi.domains.rating.model.response;

import br.com.temwifi.domains.infra.model.response.RestAbstractResponse;

import java.util.List;

public class GetRatingsResponse extends RestAbstractResponse {

    List<GetRatingResponse> ratings;

    public void setRatings(List<GetRatingResponse> ratings) {
        this.ratings = ratings;
    }
}
