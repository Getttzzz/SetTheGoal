package com.getz.setthegoal.domainpart.interactorlayer

import com.getz.setthegoal.domainpart.core.INetworkUseCase
import com.getz.setthegoal.domainpart.entitylayer.Quote

interface IGetQuoteUseCase : INetworkUseCase<Unit, Quote>