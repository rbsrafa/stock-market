import React, { Component } from 'react';
import './simulationSettings.css';

interface Props {
  settings: Function;
}

interface State {
  numberOfInvestors: number | null;
  minInvestorBudget: number | null;
  maxInvestorBudget: number | null;
  numberOfCompanies: number | null;
  minShareNumber: number | null;
  maxShareNumber: number | null;
  minSharePrice: number | null;
  maxSharePrice: number | null;
}

export default class SimulationSettings extends Component<Props, State> {

  constructor(props: Props) {
    super(props);
    this.state = {
      numberOfInvestors: 100,
      minInvestorBudget: 1000,
      maxInvestorBudget: 10000,
      numberOfCompanies: 100,
      minShareNumber: 500,
      maxShareNumber: 1000,
      minSharePrice: 10,
      maxSharePrice: 100
    }

  }

  private _updateState(e: any) {
    let state = this.state;
    (state as any)[e.target.id] = e.target.value;
    this.setState(state);
    console.log(this.state);
  }

  render() {

    return (
      <div>
        <h5>Simulation Settings</h5>
        <form>

          <div className="formGroup">
            <label className='mar-top' htmlFor="numberOfInvestors">Number of Investors</label><br/>
            <span id='demo'><strong>{this.state.numberOfInvestors}</strong></span>
            <input
              onChange={(e) => this._updateState(e)}
              type="range" min="1" max="100" value={this.state.numberOfInvestors!} className="slider" id="numberOfInvestors" />
          </div>

          <div className="formGroup">
            <label className='mar-top' htmlFor="minInvestorBudget">Min Investor Budget</label><br/>
            <span id='demo'><strong>{this.state.minInvestorBudget}</strong></span>
            <input
              onChange={(e) => this._updateState(e)}
              type="range" min="1000" max="5000" value={this.state.minInvestorBudget!} className="slider" id="minInvestorBudget" />
          </div>

          <div className="formGroup">
            <label className='mar-top' htmlFor="maxInvestorBudget">Max Investor Budget</label><br/>
            <span id='demo'><strong>{this.state.maxInvestorBudget}</strong></span>
            <input
              onChange={(e) => this._updateState(e)}
              type="range" min="5000" max="10000" value={this.state.maxInvestorBudget!} className="slider" id="maxInvestorBudget" />
          </div>

          <div className="formGroup">
            <label className='mar-top' htmlFor="numberOfCompanies">Number of Companies</label><br/>
            <span id='demo'><strong>{this.state.numberOfCompanies}</strong></span>
            <input
              onChange={(e) => this._updateState(e)}
              type="range" min="1" max="100" value={this.state.numberOfCompanies!} className="slider" id="numberOfCompanies" />
          </div>

          <div className="formGroup">
            <label className='mar-top' htmlFor="minShareNumber">Min Number of Shares</label><br/>
            <span id='demo'><strong>{this.state.minShareNumber}</strong></span>
            <input
              onChange={(e) => this._updateState(e)}
              type="range" min="500" max="750" value={this.state.minShareNumber!} className="slider" id="minShareNumber" />
          </div>

          <div className="formGroup">
            <label className='mar-top' htmlFor="maxShareNumber">Max Number of Shares</label><br/>
            <span id='demo'><strong>{this.state.maxShareNumber}</strong></span>
            <input
              onChange={(e) => this._updateState(e)}
              type="range" min="750" max="1000" value={this.state.maxShareNumber!} className="slider" id="maxShareNumber" />
          </div>

          <div className="formGroup">
            <label className='mar-top' htmlFor="minSharePrice">Min Share Price</label><br/>
            <span id='demo'><strong>{this.state.minSharePrice}</strong></span>
            <input
              onChange={(e) => this._updateState(e)}
              type="range" min="10" max="50" value={this.state.minSharePrice!} className="slider" id="minSharePrice" />
          </div>

          <div className="formGroup">
            <label className='mar-top' htmlFor="maxSharePrice">Max Share Price</label><br/>
            <span id='demo'><strong>{this.state.maxSharePrice}</strong></span>
            <input
              onChange={(e) => this._updateState(e)}
              type="range" min="50" max="100" value={this.state.maxSharePrice!} className="slider" id="maxSharePrice" />
          </div>

          <label onClick={() => this._handleRunClick()} className="btn btn-sm btn-dark mt-2">Run</label>
        </form>
      </div>
    )

  }

  private _handleRunClick() {
    this.props.settings(this.state);
  }
}
