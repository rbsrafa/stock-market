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
  }

  render() {
    return (
      <div>
        <h5>Simulation Settings</h5>
        <form>
          <div className="form-group">
            <label className='mar-top' htmlFor="numberOfInvestors">Number of Investors</label>
            <input
              onKeyUp={(e) => this._updateState(e)}
              type="text"
              className="form-control"
              id="numberOfInvestors"
              placeholder="100" />
          </div>
          <div className="form-group">
            <label className='mar-top' htmlFor="minInvestorBudget">Min Investor Budget</label>
            <input
              onKeyUp={(e) => this._updateState(e)}
              type="text"
              className="form-control"
              id="minInvestorBudget"
              placeholder="1000" />
          </div>
          <div className="form-group">
            <label className='mar-top' htmlFor="maxInvestorBudget">Max Investor Budget</label>
            <input
              onKeyUp={(e) => this._updateState(e)}
              type="text"
              className="form-control"
              id="maxInvestorBudget"
              placeholder="10000" />
          </div>
          <div className="form-group">
            <label className='mar-top' htmlFor="numberOfCompanies">Number of Companies</label>
            <input
              onKeyUp={(e) => this._updateState(e)}
              type="text"
              className="form-control"
              id="numberOfCompanies"
              placeholder="100" />
          </div>
          <div className="form-group">
            <label className='mar-top' htmlFor="minShareNumber">Min Number of Shares</label>
            <input
              onKeyUp={(e) => this._updateState(e)}
              type="text"
              className="form-control"
              id="minShareNumber"
              placeholder="500" />
          </div>
          <div className="form-group">
            <label className='mar-top' htmlFor="maxShareNumber">Max Number of Shares</label>
            <input
              onKeyUp={(e) => this._updateState(e)}
              type="text"
              className="form-control"
              id="maxShareNumber"
              placeholder="1000" />
          </div>
          <div className="form-group">
            <label className='mar-top' htmlFor="minSharePrice">Min Share Price</label>
            <input
              onKeyUp={(e) => this._updateState(e)}
              type="text"
              className="form-control"
              id="minSharePrice"
              placeholder="10" />
          </div>
          <div className="form-group">
            <label className='mar-top' htmlFor="maxSharePrice">Max Share Price</label>
            <input
              onKeyUp={(e) => this._updateState(e)}
              type="text"
              className="form-control"
              id="maxSharePrice"
              placeholder="100" />
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
