package com.getz.setthegoal.domainpart.interactorlayer

import com.getz.setthegoal.domainpart.core.IRequestableUseCase
import com.getz.setthegoal.domainpart.entitylayer.Quote

interface IGetQuoteUseCase : IRequestableUseCase<String, Quote>