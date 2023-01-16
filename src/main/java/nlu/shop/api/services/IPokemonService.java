package nlu.shop.api.services;

import nlu.shop.api.dto.PokemonDto;
import nlu.shop.api.dto.PokemonResponse;

public interface IPokemonService {
    PokemonDto createPokemon(PokemonDto pokemonDto);
    PokemonResponse getAllPokemon(int pageNo, int pageSize);
    PokemonDto getPokemonById(int id);
    PokemonDto updatePokemon(PokemonDto pokemonDto, int id);
    void deletePokemonId(int id);
}
