import React, { Component } from 'react';
import { runSimulation, getSimulations } from './services/simulationService';

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
    const simulations = await getSimulations();
    if (simulations.length > 0) {
      this.setState({ simulations });
    }
  }

  private _handleRunButton(settings: any) {
    this._runSimulation(settings);
  }

  render() {

    return (
      <React.Fragment>
        <Navbar />
        <div className="container-fluid">
          <div className="row hei">
            <div className="col-4 col-sm-3 col-md-3 col-lg-2  p-1">
              <SimulationSettings
                settings={(settings: any) => this._handleRunButton(settings)}
              />
            </div>
            {this._renderSimulations()}
          </div>
        </div>
      </React.Fragment>
    );

  }

  private async _runSimulation(settings: any) {
    await runSimulation(settings);

    const simulations = await getSimulations();
    if (simulations.length > 0) {
      this.setState({ simulations });
    }
  }

  private _renderSimulations() {
    return (
      <div className="col-8 col-sm-9 col-md-9 col-lg-10  p-1">
        {this.state.simulations ?
          (
            <div className='container'>
              {this.state.simulations ?
                (
                  this.state.simulations.map((s: any, i: any) => {
                    return <SimulationView simulation={this.state.simulations[i]} key={i} />
                  })
                ) : <div></div>
              }
            </div>
          ) : (
            <div className='container'>
              <h5 className='text-center'>No Simulations Stored</h5>
              <p className='text-center'>Please run simulations to see their results here.</p>
            </div>
          )
        }
      </div>
    )
  }

}