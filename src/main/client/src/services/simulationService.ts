 
 export async function runSimulation(settings: any){
  let request = {
    investorsQuantity: settings.numberOfInvestors,
    companiesQuantity: settings.numberOfCompanies,
    maxBudget: settings.maxInvestorBudget,
    minBudget: settings.minInvestorBudget,
    maxSharePrice: settings.maxSharePrice,
    minSharePrice: settings.minSharePrice,
    maxAmmountShares: settings.maxShareNumber,
    minAmmountShares: settings.minShareNumber
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
  return await res.json();
}

export async function getSimulations() {
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