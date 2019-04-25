import React, { Component } from 'react';

import './App.css';
import Navbar from './components/navbar/navbar';
import Simulation from './components/simulation/simulation';
import Relatories from './components/relatories/relatories';

interface Props{

}

interface State{
  settings: {
    numberOfInvestors: number | null;
    minInvestorBudget: number | null;
    maxInvestorBudget: number | null;
    numberOfCompanies: number | null;
    minShareNumber: number | null;
    maxShareNumber: number | null;
    minSharePrice: number | null;
    maxSharePrice: number | null;
  };
  
}

export default class App extends Component<Props, State> {

  constructor(props: Props){
    super(props);
    this.state = {
      settings: {
        numberOfInvestors: null,
        minInvestorBudget: null,
        maxInvestorBudget: null,
        numberOfCompanies: null,
        minShareNumber: null,
        maxShareNumber: null,
        minSharePrice: null,
        maxSharePrice: null
      }
      
    }
  }

  async componentDidUpdate(){
    if(
      this.state.settings.numberOfInvestors &&
      this.state.settings.minInvestorBudget &&
      this.state.settings.maxInvestorBudget &&
      this.state.settings.numberOfCompanies &&
      this.state.settings.minShareNumber && 
      this.state.settings.maxShareNumber &&
      this.state.settings.minSharePrice &&
      this.state.settings.maxSharePrice
    ){
      this._runSimulation()
    }
  }

  private async _runSimulation(){
    let state = this.state.settings;
    let request = {
      investorsQuantity: state.numberOfInvestors,
      companiesQuantity: state.numberOfCompanies,
      maxBudget: state.maxInvestorBudget,
      minBudget: state.minInvestorBudget,
      maxSharePrice: state.maxSharePrice,
      minSharePrice: state.minSharePrice,
      maxAmmountShares: state.maxShareNumber,
      minAmmountShares: state.minShareNumber
    }

    const res = await fetch(
      '/api/simulation/run',
      {
        method: 'POST',
        headers: {
          "Content-Type": "application/json"
        },
        body: JSON.stringify(request)
      }
    );

    console.log(res);
  }

  render() {

    return (
      <React.Fragment>
        <Navbar />
        <div className="container-fluid pt-1">
          <div className="row">
            <div className="col-4">
              <Simulation 
                settings={(settings: any) => this.setState({settings})}
              />
            </div>
            <div className="col-8">
              <Relatories />
            </div>
          </div>
        </div>
      </React.Fragment>
    );

  }

}