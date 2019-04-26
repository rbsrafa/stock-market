import React, { Component } from 'react';

import './App.css';
import Navbar from './components/navbar/navbar';
import SimulationSettings from './components/simulation/simulationSettings/simulationSettings';
import SimulationView from './components/simulation/simulationView.tsx/simulationView';

interface Props {

}

interface State {
  settings: {
    numberOfInvestors: number | null;
    minInvestorBudget: number | null;
    maxInvestorBudget: number | null;
    numberOfCompanies: number | null;
    minShareNumber: number | null;
    maxShareNumber: number | null;
    minSharePrice: number | null;
    maxSharePrice: number | null;
  }
  simulations: any
}

export default class App extends Component<Props, State> {

  constructor(props: Props) {
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
      },
      simulations: null
    }
  }

  async componentDidMount() {
    const simulations = await this.getSimulations();
    console.log(simulations);
    
    this.setState({simulations});
  }

  async componentDidUpdate() {
    if (
      this.state.settings.numberOfInvestors &&
      this.state.settings.minInvestorBudget &&
      this.state.settings.maxInvestorBudget &&
      this.state.settings.numberOfCompanies &&
      this.state.settings.minShareNumber &&
      this.state.settings.maxShareNumber &&
      this.state.settings.minSharePrice &&
      this.state.settings.maxSharePrice
    ) {
      this._runSimulation()
    }
  }

  render() {

    return (
      <React.Fragment>
        <Navbar />
        <div className="container-fluid">
          <div className="row hei">
            <div className="col-4 col-sm-3 col-md-3 col-lg-2  p-1">
              <SimulationSettings
                settings={(settings: any) => this.setState({ settings })}
              />
            </div>
            <div className="col-8 col-sm-9 col-md-9 col-lg-10  p-1">
              {this.state.simulations ?
                (
                  <div className='container'>
                    {this.state.simulations ?
                      (
                        this.state.simulations.map((s:any, i:any) => {
                          return <SimulationView simulation={this.state.simulations[i]} key={i} />
                        })
                      ) :
                      <div></div>
                    }
                    
                  </div>
                ) :
                (
                  <div className='container'>
                    <h5 className='text-center'>No Simulations Stored</h5>
                    <p>Please run simulations to see their results here.</p>
                  </div>
                )
              }
            </div>
          </div>
        </div>
      </React.Fragment>
    );

  }


  private async _runSimulation() {
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

  private async getSimulations() {
    const res = await fetch(
      '/api/simulation/relatories',
      {
        method: 'GET',
        headers: {
          "Content-Type": "application/json"
        }
      }
    );
    return await res.json();
  }

}