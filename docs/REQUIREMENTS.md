# Raw Requirements

The banking API should let customers and support teams look up account information quickly without a lot of setup. People need to see account balances, account status, and basic customer details so they can answer questions during the day. It should be possible to find an account by its identifier, and eventually the team will probably want some kind of search by customer name too.

Customers also need to move money between accounts. A customer should be able to transfer funds from one account to another, and the system should stop anything obviously invalid. The business wants protection around transfer amounts and wants good messaging when something cannot be processed, but the exact wording and all the edge cases have not always been consistent between teams. Large transfers and urgent sounding requests may need some extra attention, although the first version can keep that logic simple.

Operations wants transfer activity recorded so there is a trail when someone asks what happened. They also want some transaction history available right away for demos and support scenarios. Date filtering would be useful because support users often want to narrow down a time window, but the exact filter rules and default behavior can be clarified later.

The application needs to be easy for developers to run locally. It should start quickly, seed some example data, and work without requiring an external database or environment configuration. The workshop team also wants parts of the code to be incomplete or rough so participants can use Copilot to fill gaps, improve tests, and suggest refinements.
