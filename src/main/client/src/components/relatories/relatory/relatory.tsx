import React, { Component } from 'react'
import './relatory.css';

interface Props {
  relatory: any;
}

interface State {
  showRelatories: boolean,
  showCWHC: boolean,
  showCWLC: boolean,
  showIWHS: boolean,
  showIWLS: boolean,
  showIWHC: boolean,
  showIWLC: boolean
}

export default class Relatory extends Component<Props, State> {

  constructor(props: Props) {
    super(props)
    this.state = {
      showRelatories: false,
      showCWHC: false,
      showCWLC: false,
      showIWHS: false,
      showIWLS: false,
      showIWHC: false,
      showIWLC: false
    }
  }

  componentDidMount(){
    console.log(this.props.relatory);
  }

  render() {
    return (
      <React.Fragment>
        <div className='border bg-dark'>
          <div
            className='border bg-white hover rel'
            onClick={() => this.setState({ showCWHC: !this.state.showCWHC })}
          >
            {this._renderCompaniesWithHighestCapital()}
          </div>

          <div
            className='border bg-white hover rel'
            onClick={() => this.setState({ showCWLC: !this.state.showCWLC })}
          >
            {this._renderCompaniesWithLowestCapital()}
          </div>

          <div
            onClick={() => this.setState({ showIWHS: !this.state.showIWHS })}
            className='border bg-white hover rel'>
            {this._renderInvestorsWithHeighestShares()}
          </div>

          <div
            onClick={() => this.setState({ showIWLS: !this.state.showIWLS })}
            className='border bg-white hover rel'
          >
            {this._renderInvestorsWithLowestShares()}
          </div>

          <div
            onClick={() => this.setState({ showIWHC: !this.state.showIWHC })}
            className='border bg-white hover rel'
          >
            {this._renderInvestorsWithHeighestCompanies()}
          </div>

          <div
            onClick={() => this.setState({ showIWLC: !this.state.showIWLC })}
            className='border bg-white hover rel'
          >
            {this._renderInvestorsWithLowestCompanies()}
          </div>
        </div>
      </React.Fragment>
    )
  }

  private _renderInvestorsWithHeighestCompanies() {
    return (<div>
      <h6>Investors With Highest Number of Companies</h6>

      {this.state.showIWHC ?
        (
          this.props.relatory.investorWithHighestNumberOfCompanies.map((i: any) => {
            return this._renderInvestor(i);
          })
        ) : <div></div>}

    </div>);
  }

  private _renderInvestorsWithLowestCompanies() {
    return (<div>
      <h6>Investors With Lowest Number of Companies</h6>
      {this.state.showIWLC ?
        (
          this.props.relatory.investorWithLeastNumberOfCompanies.map((i: any) => {
            return this._renderInvestor(i);
          })
        ) : <div></div>}
    </div>);
  }

  private _renderInvestorsWithHeighestShares() {
    return (<div>
      <h6>Investors With Highest Number of Shares</h6>
      {this.state.showIWHS ?
        (
          this.props.relatory.investorsWithHighestNumberOfShares.map((i: any) => {
            return this._renderInvestor(i);
          })
        ) : <div></div>}
    </div>);
  }

  private _renderInvestorsWithLowestShares() {
    return (<div>
      <h6>Investors With Lowest Number of Shares</h6>
      {this.state.showIWLS ?
        (
          this.props.relatory.investorsWithLowestNumberOfShares.map((i: any) => {
            return this._renderInvestor(i)
          })
        ) : <div></div>}
    </div>);
  }

  private _renderInvestor(i: any) {
    return (<div key={i.id}>
      <span><strong>Id:</strong> {i.id}</span><br />
      <span><strong>Investor Type: </strong>{i.type}</span><br />
      <span><strong>Name: </strong>{i.firstName} {i.lastName}</span><br />
      <span><strong>Budget: </strong>€ {i.budget.toFixed(2)}</span><br />
      <span><strong>Number of Companies: </strong>{i.numberOfCompanies}</span><br />
      <span><strong>Number of Shares: </strong>{i.numberOfShares}</span><br />
      <span><strong>Created at:</strong> {this._timestampConverter(i.createdAt)}</span><br />
      <span><strong>Updated at:</strong> {this._timestampConverter(i.updatedAt)}</span><br /><br />
    </div>);
  }

  private _renderCompaniesWithHighestCapital() {
    return (<div>
      <h6>Companies With Highest Capital</h6>
      {this.state.showCWHC ?
        (
          this.props.relatory.companiesWithHighestCapital.map((c: any, i: any) => {
            return this._renderCompany(c, this.props.relatory.companyHighestCapital)
          })
        ) : <div></div>}
    </div>)
  }

  private _renderCompaniesWithLowestCapital() {
    return (<div>
      <h6>Companies With Lowest Capital</h6>
      {this.state.showCWLC ?
        (
          this.props.relatory.companiesWithLowestCapital.map((c: any, i: any) => {
            return this._renderCompany(c, this.props.relatory.companyLowestCapital)
          })
        ) : <div></div>}
    </div>)
  }

  private _renderCompany(c: any, capital: any) {
    return (<div key={c.id}>
      <span><strong>Id:</strong> {c.id}</span><br />
      <span><strong>Company Type: </strong>{c.type}</span><br />
      <span><strong>Name:</strong> {c.name}</span><br />
      <span><strong>Capital:</strong> € {capital.toFixed(2)}</span><br />
      <span><strong>Number of Shares:</strong> {c.numberOfShares}</span><br />
      <span><strong>Sold Shares:</strong> {c.numberOfShares - c.availableShares}</span><br />
      <span><strong>Share Price: </strong>€ {c.sharePrice.toFixed(2)}</span><br />
      <span><strong>Created at:</strong> {this._timestampConverter(c.createdAt)}</span><br />
      <span><strong>Updated at:</strong> {this._timestampConverter(c.updatedAt)}</span><br /><br />
    </div>);
  }

  private _timestampConverter(timestamp: string) {
    let date = timestamp.split('T')[0];
    let time = timestamp.split('T')[1].split('.')[0];
    return `${date} ${time}`;
  }

}
