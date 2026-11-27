/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.ufes.presenter.state;

import br.ufes.presenter.ClientePresenter;

/**
 *
 * @author Paulo Vitor
 */
public class ClientePresenterState {
    
    protected ClientePresenter presenter;

    public ClientePresenterState(ClientePresenter presenter) {
        this.presenter = presenter;
    }
        
    public void novo(){
        
        throw new RuntimeException("Novo não é permitido ness estado.");
    }
    
    public void salvar(){
        
        throw new RuntimeException("Salvar não é permitido ness estado.");
        
    }
    
    public void editar(){
        
        throw new RuntimeException("Editar não é permitido ness estado.");
        
    }
    
    public void excluir(){
        
        throw new RuntimeException("Excluir não é permitido ness estado.");
        
    }
    
    public void cancelar(){
        
        throw new RuntimeException("Cancelar não é permitido ness estado.");
        
    }
    
}
