package com.getz.setthegoal.domainpart.interactorlayer

import com.getz.setthegoal.domainpart.core.IRequestableUseCase
import com.getz.setthegoal.domainpart.entitylayer.Quote
import java.util.Locale

interface IGetQuoteUseCase : IRequestableUseCase<Locale, Quote>